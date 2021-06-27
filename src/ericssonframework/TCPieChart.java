package ericssonframework;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.UnitType;

//import test.TCPieChart;
//import test.TestReport;

public class TCPieChart extends ApplicationFrame  {

	static int Totcases = GenerateResults.tcnt;
    static int Passcases = GenerateResults.pcnt;
	static int Failcases = GenerateResults.fcnt;
	static int Skipcases = GenerateResults.scnt;
	//static int multicmndCases=GenerateResults.nacnt;
	static String resdir = GenerateResults.resultdir;
	
	
    private static final long serialVersionUID = 1L;
    {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",true));
    	
    }

    /*
   	 *=================================================
   	 * Function Name: TFPieChart
   	 * Description: Default constructor.
   	 * Parameters used: title  the frame title
   	 * Created Date: 
   	 * Modified date: 
   	 * Output: 
   	 *=================================================
   	 */
    public TCPieChart(String title) {
    	super(title);
        setContentPane(createDemoPanel());
    }

    /*
	 *=================================================
	 * Function Name: createDataset
	 * Description: To Creates a sample dataset 
	 * Parameters used: 
	 * Created Date: 
	 * Modified date: 
	 * Output: A sample dataset
	 *=================================================
	 */
    private static PieDataset createDataset() {
    	
    	System.out.println(Totcases);
    	System.out.println(Passcases);
    	System.out.println(Failcases);
    	System.out.println(Skipcases);
    	//System.out.println(multicmndCases);
    
    	

    	System.out.println("TCPieChart class");
    	System.out.println("TCTotcases: "+Totcases); 	
    	System.out.println("TCresdir: "+resdir); 	

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Failed("+Failcases+")", Failcases);
        dataset.setValue("Skipped("+Skipcases+")", Skipcases);
        dataset.setValue("Passed("+Passcases+")", Passcases);
        //dataset.setValue("NA("+multicmndCases+")", multicmndCases);
        return dataset;
    }

    /*
	 *=================================================
	 * Function Name: createChart
	 * Description: To Create a chart
	 * Parameters used: dataset
	 * Created Date: 
	 * Modified date: 
	 * Output: A chart
	 *=================================================
	 */
    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(
            "TestCase Execution Status",  // chart title
            dataset,            // data
            false,              // no legend
            true,               // tooltips
            false               // no URL generation
        );

        // set a custom background for the chart
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), 
                new Color(20, 20, 20), new Point(300, 200), Color.WHITE));
        
        //set Background color
        chart.setBackgroundPaint(Color.white);

        // customise the title position and font
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.CENTER);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 20));
        t.setPaint(Color.BLACK);
        PiePlot plot = (PiePlot) chart.getPlot();
       
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);

        // use gradients and white borders for the section colours
        plot.setSectionPaint("Skipped("+Skipcases+")", createGradientPaint(new Color(200, 200, 255), Color.BLUE));
        plot.setSectionPaint("Failed("+Failcases+")", createGradientPaint(new Color(255, 200, 200), Color.RED));
        plot.setSectionPaint("Passed("+Passcases+")", createGradientPaint(new Color(200, 255, 200), Color.GREEN));
        //plot.setSectionPaint("NA("+multicmndCases+")", createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // customise the section label appearance
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 15));
        plot.setLabelLinkPaint(Color.BLACK);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.BLACK);
        plot.setLabelBackgroundPaint(null);
        
        FileOutputStream out;
       
		try {
		//	out = new FileOutputStream( new File( "" + resdir + "\\TestCase.png" ) );
			out = new FileOutputStream( new File( "..\\..\\TestInputs\\TestCase.jpg" ) );
			ChartUtilities.writeChartAsPNG( out, chart, 500, 300 ); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        
        return chart;

    }

   
    /*
   	 *=================================================
   	 * Function Name: RadialGradientPaint
   	 * Description: A utility method for creating gradient paints
   	 * Parameters used: color 1, color 2.
     * Created Date: 
   	 * Modified date: 
   	 * Output: A radial gradient paint
   	 *=================================================
   	 */
    private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }

  
    /*
   	 *=================================================
   	 * Function Name: createDemoPanel
   	 * Description: Creates a panel for the chart
   	 * Parameters used:
     * Created Date: 
   	 * Modified date: 
   	 * Output: A panel
   	 *=================================================
   	 */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(UnitType.ABSOLUTE,4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }

     public static void main(String[] args) {

        TCPieChart chart = new TCPieChart("TC level Execution chart");

    }
}
