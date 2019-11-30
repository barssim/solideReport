package report;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import models.ArticleBean;
import reportUtils.DatabaseClass;
import reportUtils.NoRanger;
import reportUtils.ServerConfiguration;

public class ArticleReportCreator {

	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		ReportFactory reportFactory = new ReportFactory();
		List<ArticleBean> myArticlesList = null;
		ReportReceiver reportReceiver = new ReportReceiver();
		int nextReport = 0;
		Connection con = null;
		File createdArticlesFile = null;
		clsConfig.confige();
				
			nextReport = NoRanger.getNext("Report");
		
		ReportFile reportArticle = reportFactory.getReportFile("ARTICLES");

		DatabaseClass clsDB = clsConfig.clsDB;
		con = clsDB.getConnection();
		con.setAutoCommit(false);
		try {
			createdArticlesFile = reportArticle.createReport(nextReport, "ArticleReport");
			NoRanger.incrementNextNo("Report");
			con.commit();
			myArticlesList = reportReceiver.receivedArticles();
			
			
			if( !myArticlesList.isEmpty())
			{
				reportArticle.writeInFile(createdArticlesFile.getPath(), clsConfig.fileencoding, clsConfig.delimiter, myArticlesList);
			}
		}

		catch (Exception ex) {
			con.rollback();

		} finally {
			con.setAutoCommit(true);
		}

	}
}
