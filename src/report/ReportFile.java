package report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import models.ArticleBean;
import reportUtils.ServerConfiguration;

public abstract class ReportFile {

	public static final ServerConfiguration clsConfig = ServerConfiguration.getInstance();
	
	public File createReport(int nextReport, String ReportType) {
		return new File(	clsConfig.OutputPath + System.getProperty("file.separator") + ReportType  + nextReport + ".txt" );
		 
	}
	public abstract void writeInFile( String strFilePathAndName, String strEncoding, String strDelimiter, List<ArticleBean> rowsList) throws IOException;
}
