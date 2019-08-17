package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SalesApp {
	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
		if (salesId == null) {
			return;
		}

		SalesDao salesDao = generateSalesDao();
		SalesReportDao salesReportDao = generateSalesReportDao();

		Sales sales = salesDao.getSalesBySalesId(salesId);

		Date today = new Date();
		if (!sales.isEffectiveToday(today)){
			return;
		}
		
		List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

		// Hi, I am Jimmy Ou. I think the code left should use the filteredReportDataList instead of reportDataList, which is weird.
		// I did not know whether it is a mistake or just I went wrong :).
		// But I had no time to figure out it since you were hurry to catch the train.
		// Mercy please ⊙﹏⊙∥.

		List<SalesReportData> filteredReportDataList = filterReportData(isSupervisor, reportDataList);

		filteredReportDataList = generateLimitedReport(filteredReportDataList, maxRow);

		List<String> headers = generateHeader(isNatTrade);

		SalesActivityReport report = this.generateReport(headers, filteredReportDataList);
		
		EcmService ecmService = generateEcmService();
		ecmService.uploadDocument(report.toXml());
		
	}

	protected List<SalesReportData> filterReportData(boolean isSupervisor, List<SalesReportData> reportDataList) {
		List<SalesReportData> filteredReportDataList = reportDataList.stream().filter((data) -> {
			if ("SalesActivity".equalsIgnoreCase(data.getType())) {
				boolean notSensitive = !data.isConfidential() || (data.isConfidential() && isSupervisor);
				return notSensitive;
			}
			return false;
		}).collect(Collectors.toList());
		return filteredReportDataList;
	}

	protected List<String> generateHeader(boolean isNatTrade) {
		if (isNatTrade) {
			return Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			return Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
	}

	protected List<SalesReportData> generateLimitedReport(List<SalesReportData> reportDataList, int maxRow) {
		int size = Math.min(reportDataList.size(), maxRow);
		List<SalesReportData> tempList = reportDataList.stream().limit(size).collect(Collectors.toList());
		return tempList;
	}

	protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
		// TODO Auto-generated method stub
		return new SalesActivityReport();
	}

	protected SalesDao generateSalesDao() {
		return new SalesDao();
	}

	protected SalesReportDao generateSalesReportDao() {
		return new SalesReportDao();
	}

	protected EcmService generateEcmService() {
		return new EcmService();
	}

}
