package com.iteye.wwwcomy.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 * 通过barCode4j生成条形码
 * 
 * @author wwwcomy
 * 
 */
public class BarCodeUtil {
	public static void main(String[] args) {
		File file = new File("d:/1.jpg");
		File code39File = new File("d:/2.jpg");
		generateCode128Barcode(file, "12345678");
		generateCode39Barcode(code39File, "12345678");
	}

	public static void generateCode128Barcode(File file, String code) {
		Code128Bean bean = new Code128Bean();
		int dpi = 150;
		bean.setModuleWidth(0.21);
		bean.setHeight(15);
		bean.doQuietZone(true);
		bean.setQuietZone(2);
		// 两边空白区//human-readable
		bean.setFontName("Helvetica");
		bean.setFontSize(3);
		bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
					"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, true, 0);
			bean.generateBarcode(canvas, code);
			canvas.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void generateCode39Barcode(File file, String code) {
		Code39Bean bean = new Code39Bean();
		int dpi = 150;
		bean.setModuleWidth(0.21);
		bean.setHeight(15);
		bean.doQuietZone(true);
		bean.setQuietZone(2);
		// 两边空白区//human-readable
		bean.setFontName("Helvetica");
		bean.setFontSize(3);
		bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
					"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, true, 0);
			bean.generateBarcode(canvas, code);
			canvas.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
