package proj.basic.mall;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlink;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import proj.basic.item.model.ItemVO;
import proj.basic.itemmedia.controller.ShowItemMediaService;
import proj.basic.itemmedia.model.ItemMediaVO;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportProcess {

	public XWPFDocument exportWord(ItemVO itemVO, String url) {
		// XWPFDocument dFile = new XWPFDocument();

		CustomXWPFDocument dFile = new CustomXWPFDocument();

		XWPFParagraph p = dFile.createParagraph();
		XWPFRun run1 = p.createRun();
		run1.setFontSize(16);
		run1.setBold(true);
		run1.setText("商品編號：" + itemVO.getItemNo());
		run1.addBreak();
		run1.setText("商品名稱：" + itemVO.getItemName());
		run1.addBreak();
		p = dFile.createParagraph();
		run1 = p.createRun();
		run1.setText("商品介紹：");
		run1.addBreak();
		run1.setText(itemVO.getItemDscrp());
		run1.addBreak();
		run1 = p.createRun();
		run1.setBold(true);
		run1.addBreak();
		run1.setText("商品價格：" + itemVO.getPrice());
		run1.addBreak();

		// 使用網路上的圖片置入解法
		XWPFParagraph paragraphX = dFile.createParagraph();
		paragraphX.setAlignment(ParagraphAlignment.CENTER);

		ShowItemMediaService mediaSrv = new ShowItemMediaService();
		List<ItemMediaVO> iMediaList = mediaSrv.showItem(itemVO.getItemNo());
		String blipId = null;
		for (ItemMediaVO iMedia : iMediaList) {
			if (iMedia.getMediaType().substring(0, 5).equals("image"))
				try {
					blipId = paragraphX.getDocument().addPictureData(iMedia.getItemMedia(),XWPFDocument.PICTURE_TYPE_JPEG);
					System.out.println("4" + blipId);
					dFile.createPicture(blipId, dFile.getNextPicNameNumber(org.apache.poi.xwpf.usermodel.Document.PICTURE_TYPE_JPEG), 300, 350);
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				}
		}

		// 圖片BUG
		// ShowItemMediaService mediaSrv = new ShowItemMediaService();
		// List<ItemMediaVO> iMediaList = mediaSrv.showItem(itemVO.getItemNo());
		//
		// for(ItemMediaVO iMedia:iMediaList){
		// if(iMedia.getMediaType().substring(0, 5).equals("image"))
		// try {
		// // run1.addPicture(new ByteArrayInputStream(iMedia.getItemMedia()),
		// XWPFDocument.PICTURE_TYPE_JPEG, iMedia.getItemMediaNo().toString(),
		// Units.toEMU(500), Units.toEMU(500));
		// run1.setText("picture for this item : (size= " +
		// iMedia.getItemMedia().length + " )");
		// run1.addBreak();
		// dFile.addPictureData(iMedia.getItemMedia(),
		// XWPFDocument.PICTURE_TYPE_JPEG);
		// } catch (InvalidFormatException e) {
		// e.printStackTrace();
		// }
		// }
		
		run1.setText("點選網址購買：");
		run1.addBreak();
			
		//Add the link as External relationship
        String id = p.getDocument().getPackagePart().addExternalRelationship(url, XWPFRelation.HYPERLINK.getRelation()).getId();

        //Append the link and bind it to the relationship
        CTHyperlink cLink = p.getCTP().addNewHyperlink();
        cLink.setId(id);

        //Create the linked text
        CTText ctText = CTText.Factory.newInstance();
        ctText.setStringValue(url);
        CTR ctr = CTR.Factory.newInstance();
        ctr.setTArray(new CTText[]{ctText});

        //Insert the linked text into the link
        cLink.setRArray(new CTR[]{ctr});

		return dFile;
	}

	public com.itextpdf.text.Document ExportPDF(ItemVO itemVO, String url, OutputStream out) {
		String title = "的說明文件";
		String author = "WOW商城";

		com.itextpdf.text.Document pdfFile = new com.itextpdf.text.Document(PageSize.A4);
		try {
			PdfWriter pWriter = PdfWriter.getInstance(pdfFile, out);
			BaseFont twFont = BaseFont.createFont("C:\\windows\\fonts\\KAIU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			pdfFile.addAuthor(author);
			
			Font f = new Font(twFont, 12);
			pdfFile.open();
			pdfFile.add(new Paragraph(itemVO.getItemName() + title, f));
			
			List<ItemMediaVO> iMediaList = new ShowItemMediaService().showItem(itemVO.getItemNo());
			Image img = null;
			for(ItemMediaVO iMediaVO:iMediaList){
				img = Image.getInstance(iMediaVO.getItemMedia());
				img.scalePercent(70f);
				pdfFile.add(img);
			}
			 
			pdfFile.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdfFile;
	}

}
