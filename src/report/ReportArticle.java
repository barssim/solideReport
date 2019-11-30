package report;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import models.ArticleBean;
import reportUtils.Formater;
import reportUtils.ServerConfiguration;

public class ReportArticle extends ReportFile {

	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	/**
	 * writes data in Report file
	 * 
	 */
	@Override
	public void writeInFile(String strFilePathAndName, String strEncoding, String strDelimiter,
			List<ArticleBean> rowsList) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(strFilePathAndName, false), strEncoding));
		allExistingArticles(bufferedWriter, strEncoding, strDelimiter, rowsList);
	}

	/**
	 * 
	 * @param strFilePathAndName
	 * @param strEncoding
	 * @param strDelimiter
	 * @param rowsList
	 * @throws UnsupportedEncodingException
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void allExistingArticles(BufferedWriter bufferedWriter, String strEncoding, String strDelimiter,
			List<ArticleBean> rowsList) throws UnsupportedEncodingException, FileNotFoundException, IOException {

		try {
			StringBuilder strHeadLine = new StringBuilder();
			strHeadLine.append(Formater.format("ARTICLENAME", strDelimiter, " "))
					.append(Formater.format("ARTICLECATEGORY", strDelimiter, " "))
					.append(Formater.format("ARTICLETYPE", strDelimiter, " "));

			bufferedWriter.write(strHeadLine.toString());
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			for (ArticleBean articleBean : rowsList) {

				{
					StringBuilder strToWrite = new StringBuilder();
					strToWrite.append(Formater.format(articleBean.getArticleName(), strDelimiter, " "))
							.append(Formater.format(articleBean.getArticleCategory(), strDelimiter, " "))
							.append(Formater.format(articleBean.getArticleType(), strDelimiter, " "));

					// write line
					bufferedWriter.write(strToWrite.toString());
					bufferedWriter.newLine();
				}
				
			}
			bufferedWriter.newLine();
			bufferedWriter.write(Formater.format("Total", strDelimiter, " ") + rowsList.size());
			bufferedWriter.close();

		} catch (IOException ex) {
			bufferedWriter.close();
		}
	}



}
