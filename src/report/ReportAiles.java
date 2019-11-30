package report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import models.ArticleBean;
import reportUtils.Formater;
import reportUtils.ServerConfiguration;

public class ReportAiles extends ReportFile {

//	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
    private static final String AILE_CATEGORYNAME = "Ailes";
    private static final int AILE_CATEGORYNO = 6;
    private static final String EMPTY =  "Choisir...";
    
	@Override
	public void writeInFile(String strFilePathAndName, String strEncoding, String strDelimiter,
			List<ArticleBean> rowsList) throws IOException {

		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(strFilePathAndName, false), strEncoding));) {
			StringBuilder strHeadLine = new StringBuilder();
			strHeadLine.append(Formater.format("ARTICLENAME", strDelimiter, " "))
					.append(Formater.format("ARTICLECATEGORY", strDelimiter, " "))
					.append(Formater.format("ARTICLETYPE", strDelimiter, " "));

			bufferedWriter.write(strHeadLine.toString());
			bufferedWriter.newLine();
			for (ArticleBean articleBean : rowsList) {

				StringBuilder strToWrite = new StringBuilder();
				strToWrite.append(Formater.format(articleBean.getArticleName(), strDelimiter, " "))
						.append(Formater.format(articleBean.getArticleCategory(), strDelimiter, " "))
						.append(Formater.format(articleBean.getArticleType(), strDelimiter, " "));

				// write line
				bufferedWriter.write(strToWrite.toString());
				bufferedWriter.newLine();

			}
			bufferedWriter.newLine();
			bufferedWriter.write(Formater.format("Total", strDelimiter, " ") + rowsList.size());
		}
 
	}

	

}
