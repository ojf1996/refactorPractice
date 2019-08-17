package sales;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport() {
		String salesId = "DUMMY";
		Long oneDay = 24L * 60 * 60 * 1000;
		Date now = new Date(0);
		Date tomorrow = new Date(now.getTime() + oneDay);

		Sales mockedSales = spy(Sales.class);
		when(mockedSales.getEffectiveTo()).thenReturn(now);
		when(mockedSales.getEffectiveFrom()).thenReturn(tomorrow);

		SalesDao mockedSalesDao = mock(SalesDao.class);
		when(mockedSalesDao.getSalesBySalesId(salesId)).thenReturn(mockedSales);

		SalesReportData mockSalesReportData = spy(new SalesReportData());
		mockSalesReportData.setConfidential(false);
		when(mockSalesReportData.getType()).thenReturn("SalesActivity");
		List<SalesReportData> mockSalesReportDataList = Arrays.asList(mockSalesReportData);

		SalesReportDao mockSaleReportDao = mock(SalesReportDao.class);
		when(mockSaleReportDao.getReportData(mockedSales)).thenReturn(mockSalesReportDataList);

		EcmService mockedEcmService = mock(EcmService.class);

		SalesActivityReport mockedReport = mock(SalesActivityReport.class);
		when(mockedReport.toXml()).thenReturn("HAHA I am mocking");

		SalesApp salesApp = spy(new SalesApp());
		when(salesApp.generateSalesDao()).thenReturn(mockedSalesDao);
		when(salesApp.generateSalesReportDao()).thenReturn(mockSaleReportDao);
		when(salesApp.generateEcmService()).thenReturn(mockedEcmService);
		when(salesApp.generateReport(any(), any())).thenReturn(mockedReport);

		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

		verify(mockedEcmService).uploadDocument("HAHA I am mocking");
	}

	@Test
	public void should_return_before_invoke_other_function_when_salesId_isNull() {
		String salesId = "DUMMY";
		Long oneDay = 24L * 60 * 60 * 1000;
		Date now = new Date(0);
		Date tomorrow = new Date(now.getTime() + oneDay);

		Sales mockedSales = spy(Sales.class);
		when(mockedSales.getEffectiveTo()).thenReturn(now);
		when(mockedSales.getEffectiveFrom()).thenReturn(tomorrow);

		SalesDao mockedSalesDao = mock(SalesDao.class);
		when(mockedSalesDao.getSalesBySalesId(salesId)).thenReturn(mockedSales);

		SalesReportData mockSalesReportData = spy(new SalesReportData());
		mockSalesReportData.setConfidential(false);
		when(mockSalesReportData.getType()).thenReturn("SalesActivity");
		List<SalesReportData> mockSalesReportDataList = Arrays.asList(mockSalesReportData);

		SalesReportDao mockSaleReportDao = mock(SalesReportDao.class);
		when(mockSaleReportDao.getReportData(mockedSales)).thenReturn(mockSalesReportDataList);

		EcmService mockedEcmService = mock(EcmService.class);

		SalesActivityReport mockedReport = mock(SalesActivityReport.class);
		when(mockedReport.toXml()).thenReturn("HAHA I am mocking");

		SalesApp salesApp = spy(new SalesApp());
		when(salesApp.generateSalesDao()).thenReturn(mockedSalesDao);
		when(salesApp.generateSalesReportDao()).thenReturn(mockSaleReportDao);
		when(salesApp.generateEcmService()).thenReturn(mockedEcmService);
		when(salesApp.generateReport(any(), any())).thenReturn(mockedReport);

		salesApp.generateSalesActivityReport(null, 1000, false, false);

		verify(mockSaleReportDao, times(0)).getReportData(mockedSales);
		verify(mockedSalesDao, times(0)).getSalesBySalesId(any());
		verify(mockedEcmService, times(0)).uploadDocument("HAHA I am mocking");
	}

    @Test
    public void should_return_before_invoke_other_function_when_date_is_effective() {
        String salesId = "DUMMY";
        Long oneDay = 24L * 60 * 60 * 1000;
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + oneDay);

        Sales mockedSales = spy(Sales.class);
        when(mockedSales.getEffectiveTo()).thenReturn(tomorrow);
		when(mockedSales.getEffectiveFrom()).thenReturn(new Date(0));

        SalesDao mockedSalesDao = mock(SalesDao.class);
        when(mockedSalesDao.getSalesBySalesId(salesId)).thenReturn(mockedSales);

        SalesReportData mockSalesReportData = spy(new SalesReportData());
        mockSalesReportData.setConfidential(false);
        when(mockSalesReportData.getType()).thenReturn("SalesActivity");
        List<SalesReportData> mockSalesReportDataList = Arrays.asList(mockSalesReportData);

        SalesReportDao mockSaleReportDao = mock(SalesReportDao.class);
        when(mockSaleReportDao.getReportData(mockedSales)).thenReturn(mockSalesReportDataList);

        EcmService mockedEcmService = mock(EcmService.class);

        SalesActivityReport mockedReport = mock(SalesActivityReport.class);
        when(mockedReport.toXml()).thenReturn("HAHA I am mocking");

        SalesApp salesApp = spy(new SalesApp());
        when(salesApp.generateSalesDao()).thenReturn(mockedSalesDao);
        when(salesApp.generateSalesReportDao()).thenReturn(mockSaleReportDao);
        when(salesApp.generateEcmService()).thenReturn(mockedEcmService);
        when(salesApp.generateReport(any(), any())).thenReturn(mockedReport);

        salesApp.generateSalesActivityReport(salesId, 1000, false, false);

        verify(mockedSalesDao).getSalesBySalesId(any());
        verify(mockSaleReportDao, times(0)).getReportData(mockedSales);
        verify(mockedEcmService, times(0)).uploadDocument("HAHA I am mocking");
    }

    @Test
    public void should_filter_sensitive_data_when_invoke_filterReportData_and_is_not_supervisor() {
        SalesReportData mockSalesReportData1 = spy(new SalesReportData());
        mockSalesReportData1.setConfidential(false);
        when(mockSalesReportData1.getType()).thenReturn("SalesActivity");
        SalesReportData mockSalesReportData2 = spy(new SalesReportData());
        mockSalesReportData2.setConfidential(true);
        when(mockSalesReportData2.getType()).thenReturn("SalesActivity");
        List<SalesReportData> mockSalesReportDataList = Arrays.asList(mockSalesReportData1, mockSalesReportData2);

        SalesApp salesApp = new SalesApp();
        List<SalesReportData> filterResult = salesApp.filterReportData(false, mockSalesReportDataList);

        Assert.assertEquals(1, filterResult.size());
    }

    @Test
    public void should_return_all_sensitive_data_when_invoke_filterReportData_and_is_supervisor() {
        SalesReportData mockSalesReportData1 = spy(new SalesReportData());
        mockSalesReportData1.setConfidential(true);
        when(mockSalesReportData1.getType()).thenReturn("SalesActivity");
        SalesReportData mockSalesReportData2 = spy(new SalesReportData());
        mockSalesReportData2.setConfidential(true);
        when(mockSalesReportData2.getType()).thenReturn("SalesActivity");
        List<SalesReportData> mockSalesReportDataList = Arrays.asList(mockSalesReportData1, mockSalesReportData2);

        SalesApp salesApp = new SalesApp();
        List<SalesReportData> filterResult = salesApp.filterReportData(true, mockSalesReportDataList);

        Assert.assertEquals(2, filterResult.size());
    }
}
