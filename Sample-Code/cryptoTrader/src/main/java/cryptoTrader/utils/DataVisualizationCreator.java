package cryptoTrader.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;

import cryptoTrader.gui.MainUI;
import cryptoTrader.main.Result;
import cryptoTrader.main.ResultFactory;

public class DataVisualizationCreator {
	
	public void createCharts(ResultFactory results) {

		createTableOutput(results);
		createBar(results);
		
	}

	
	private void createTableOutput(ResultFactory resultList) {
		// Dummy dates for demo purposes. These should come from selection menu
		Object[] columnNames = {"Trader","Strategy","CryptoCoin","Action","Quantity","Price","Date"};
		
		// Dummy data for demo purposes. These should come from actual fetcher
//		Object[][] data = {
//				{"Trader-5", "Strategy-A", "ETH", "Buy", "500", "150.3","13-January-2022"},
//				{"Trader-2", "Strategy-B", "BTC", "Sell", "200", "50.2","13-January-2022"},
//				{"Trader-3", "Strategy-C", "USDT", "Buy", "1000", "2.59","15-January-2022"},
//				{"Trader-1", "Strategy-A", "USDC", "Buy", "500", "150.3","16-January-2022"},
//				{"Trader-2", "Strategy-B", "ADA", "Sell", "200", "50.2","16-January-2022"},
//				{"Trader-3", "Strategy-C", "SOL", "Buy", "1000", "2.59","17-January-2022"},
//				{"Trader-1", "Strategy-A", "ONE", "Buy", "500", "150.3","17-January-2022"},
//				{"Trader-2", "Strategy-B", "MANA", "Sell", "200", "50.2","17-January-2022"},
//				{"Trader-3", "Strategy-C", "AVAX", "Buy", "1000", "2.59","19-January-2022"},
//				{"Trader-1", "Strategy-A", "LUNA", "Buy", "500", "150.3","19-January-2022"},
//				{"Trader-2", "Strategy-B", "FTM", "Sell", "200", "50.2","19-January-2022"},
//				{"Trader-3", "Strategy-C", "HNT", "Buy", "1000", "2.59","20-January-2022"}
//		};
		Object[][] data = resultList.getResultObject();

		JTable table = new JTable(data, columnNames);
		//table.setPreferredSize(new Dimension(600, 300));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Trader Actions",
                TitledBorder.CENTER,
                TitledBorder.TOP));
		
	
		
		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);;
		
		MainUI.getInstance().updateStats(scrollPane);
	}

	
	private void createBar(ResultFactory results) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Object[][] data = results.getResultObject();
		
		String clientName, stratName;
		for (Object[] datum : data) {
			if (datum[3] != null && datum[3] != "Fail") {
				clientName = datum[0].toString();
				stratName = datum[1].toString();
				if (isIn(dataset, clientName, stratName)) {
					dataset.incrementValue(1, clientName, stratName);
				}
				else {
					dataset.setValue(1, clientName, stratName);
				}

			}
		}
		

		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Strategy");
		plot.setDomainAxis(domainAxis);
		LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
		rangeAxis.setRange(new Range(0.1, 20.0));
		plot.setRangeAxis(rangeAxis);

		//plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		//plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}
	  private boolean isIn(DefaultCategoryDataset dataset, String clientName, String stratName) {
        try {
            dataset.getValue(clientName, stratName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}