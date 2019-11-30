package report;


public class ReportFactory {

	// use getReportFile method to get object of type ReportFile
		public ReportFile getReportFile(String ReportFileType) {
			if (ReportFileType == null) {
				return null;
			}
			if (ReportFileType.equalsIgnoreCase("AILES")) {
				return new ReportAiles();

			} else if (ReportFileType.equalsIgnoreCase("ARTICLES")) {
				return new ReportArticle() ;

			}
			return null;
		}
	
}
