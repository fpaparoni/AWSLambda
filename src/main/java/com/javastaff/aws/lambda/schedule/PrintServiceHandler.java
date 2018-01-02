package com.javastaff.aws.lambda.schedule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class PrintServiceHandler implements RequestHandler<CloudWatchEvent, String>{

	public String handleRequest(CloudWatchEvent arg0, Context arg1) {
		try {
            URL feedUrl = new URL("http://www.repubblica.it/rss/homepage/rss2.0.xml");

            SyndFeedInput input = new SyndFeedInput();
            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
            //HttpURLConnection conn = (HttpURLConnection)feedUrl.openConnection(proxy);
            HttpURLConnection conn = (HttpURLConnection)feedUrl.openConnection();
            InputStream in = conn.getInputStream();
            SyndFeed feed = (SyndFeed) input.build(new InputStreamReader(in, Charset.forName("UTF-8")));
            
            //PDF
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            
            for(SyndEntry entry:feed.getEntries()) {
            	System.out.println(entry.getTitle()+" "+entry.getLink());
            	URL tempUrl=new URL(entry.getLink());
            	//HttpURLConnection conn2 = (HttpURLConnection)tempUrl.openConnection(proxy);
            	HttpURLConnection conn2 = (HttpURLConnection)tempUrl.openConnection();
                InputStream in2 = conn2.getInputStream();
            	String text=ArticleExtractor.INSTANCE.getText(new InputStreamReader(in2));
            	Chunk chunk = new Chunk(entry.getTitle(), chapterFont);
                Chapter chapter = new Chapter(new Paragraph(chunk), 1);
                chapter.setNumberDepth(0);
                chapter.add(new Paragraph(text, paragraphFont));
                document.add(chapter);
                break;
            }
            
            document.close();
            
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            		.withRegion("eu-west-3")
            		.build();
            
            ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
            
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(bais.available());
            
            s3.putObject("giornali","prova.pdf",bais,meta);
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
		
		return "YEAH";
	}
}
