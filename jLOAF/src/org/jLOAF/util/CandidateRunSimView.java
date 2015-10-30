//package org.jLOAF.util;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart.Data;
//import javafx.scene.chart.XYChart.Series;
//import javafx.stage.Stage;
//
//import org.jLOAF.action.AtomicAction;
//import org.jLOAF.action.ComplexAction;
//import org.jLOAF.casebase.Case;
//import org.jLOAF.inputs.AtomicInput;
//import org.jLOAF.inputs.ComplexInput;
//import org.jLOAF.reasoning.BestRunReasoning;
//import org.jLOAF.retrieve.kNNUtil;
//import org.jLOAF.retrieve.sequence.weight.DecayWeightFunction;
//import org.jLOAF.retrieve.sequence.weight.FixedWeightFunction;
//import org.jLOAF.retrieve.sequence.weight.GaussianWeightFunction;
//import org.jLOAF.retrieve.sequence.weight.LinearWeightFunction;
////import org.jLOAF.retrieve.sequence.weight.TimeVaryingWeightFunction;
//import org.jLOAF.retrieve.sequence.weight.WeightFunction;
//import org.jLOAF.sim.atomic.ActionEquality;
//import org.jLOAF.sim.atomic.InputEquality;
//import org.jLOAF.sim.complex.ActionMean;
//import org.jLOAF.sim.complex.InputMean;
//import org.jLOAF.tools.LeaveOneOut;
//import org.jLOAF.tools.TestingTrainingPair;
//import org.jLOAF.util.JLOAFLogger.JLOAFLoggerInfoBundle;
//import org.jLOAF.util.helper.LfOTraceParser;
//
//import java.util.Scanner;
//
//public class CandidateRunSimView extends Application implements Observer{
//	private LineChart<String, Number> lineChart;
//	
//	private Series<String, Number> current;
//	
//	private ArrayList<Series<String, Number>> allData;
//	
//	private Series<String, Number> weights;
//	
//	private static final int DATA_SIZE = 50;
//	private static final Show SHOW_TYPE = Show.ALL; // 0 = all, 1 = action, 2 = state
//	
//	enum Show{
//		ALL,
//		ACTION,
//		STATE
//	};
//
//	String[] traceFiles={"FixedSequenceAgent.trace","SmartRandomAgent.trace", 
//						"SmartRandomExplorerAgent.trace", "SmartStraightLineAgent.trace",
//						"ZigzagAgent.trace"};
//	String[] weightFunctions = {"DecayWeightFunction.java","FixedWeightFunction.java",
//								"GaussianWeightFunction.java", "LinearWeightFunction.java",};
//	// TimeVaryingWeightFunction not included as constructor has not been initialized
//
//	
//	public CandidateRunSimView(){
//		this.allData = new ArrayList<Series<String, Number>>();
//		
////		SwingUtilities.invokeLater(new Runnable() {
////	   	@Override
////		public void run() {
////	    	guiSetup();
////	    }
////	});
//	}
//	
//	@Override
//	public void start(Stage stage) throws Exception {
//		stage.setTitle("Line Chart Sample");
//		final CategoryAxis xAxis = new CategoryAxis();
//        final NumberAxis yAxis = new NumberAxis(0, 1.1, 0.05);
//        lineChart = new LineChart<String,Number>(xAxis,yAxis);
//        
//        Scene scene  = new Scene(lineChart,800,600); 
//        stage.setScene(scene);
//        stage.show();
//        
//        ComplexInput.setClassStrategy(new InputMean());
//		AtomicInput.setClassStrategy(new InputEquality());
//		AtomicAction.setClassStrategy(new ActionEquality());
//		ComplexAction.setClassStrategy(new ActionMean());
//		
//		Scanner a = new Scanner(System.in);
//		
//		int inputWeight = -1;
//		double parameter = 0;
//		double parameter2 = 0;
//		
//		while(inputWeight < 0 || inputWeight > 3){
//			System.out.println("Select a Weight Function (Enter Integer Only)");
//			for(int i = 0; i < weightFunctions.length; i++){
//				System.out.println(i + " - " + weightFunctions[i]);
//			}
//				inputWeight = Integer.parseInt(a.nextLine());
//		}
//		System.out.println("Enter the parameter value");
//		parameter = Double.parseDouble(a.nextLine());
//		
//		if(inputWeight == 2){
//			System.out.println("Enter the second parameter value");
//			parameter2 = Double.parseDouble(a.nextLine());
//		}
//		
//		
//		WeightFunction f = new FixedWeightFunction(1);	// default value
//		switch (inputWeight) {
//        	case 0:
//        		f = new DecayWeightFunction(parameter);
//                break;
//        	case 1:
//        		f = new FixedWeightFunction(parameter);
//        		break;
//        	case 2:
//        		f = new GaussianWeightFunction(parameter, parameter2);
//        		break;
//        	case 3:
//        		f = new LinearWeightFunction(parameter);
//        		break;
////        	case 4:
////        		f = new TimeVaryingWeightFunction(parameter);
////        		break;
//		}
//		
//		
////		WeightFunction f = new FixedWeightFunction(1);
//		kNNUtil.setWeightFunction(f);
//		
//		weights = new Series<String, Number>();
//		for (int i = 0; i < DATA_SIZE; i++){
//			if(SHOW_TYPE != Show.ACTION){
//				weights.getData().add(new Data<String, Number>("" + i, f.getWeightValue(i)));
//			}
//			if (i + 0.5 < (DATA_SIZE - 1) && (SHOW_TYPE != Show.STATE)){
//				weights.getData().add(new Data<String, Number>("" + (i + 0.5), f.getWeightValue(i)));
//			}
//		}
//		weights.setName("Weight");
//        
//
//		int inputTrace = -1;
//		while(inputTrace < 0 || inputTrace > 4){
//			System.out.println("Select a Trace Folder (Enter Integer Only)");
//		for(int i = 0; i < traceFiles.length; i++){
//			System.out.println(i + " - " + traceFiles[i]);
//		}
//			inputTrace = Integer.parseInt(a.nextLine());
//		}
////      String fileLocal = "C:/Users/calebchan/Desktop/Stuff/workspace/Test Data/Batch Test 3/TB/Expert/Run 1/SmartRandomAgent.trace";
//		// old initilization for local file before console input
//		
////		String fileLocal = "/home/labadmin/Documents/Run 1/SmartRandomAgent.trace";
//		// for work computer testing
//		
//		String fileLocal = "/home/labadmin/Documents/Run 1/" + traceFiles[inputTrace];
//		// for work computer
//		
////		String fileLocal = "C:/Users/calebchan/Desktop/Stuff/workspace/Test Data/Batch Test 3/TB/Expert/Run 1/" + traceFiles[inputTrace];
//		// for Caleb's home computer
//		LfOTraceParser parser = new LfOTraceParser(fileLocal);
//        
//        if (!parser.parseFile()){
//        	throw new RuntimeException("Failed to Parse");
//        }
//        parser.saveCaseBase("TestCaseBase.cb");
//        LeaveOneOut l = LeaveOneOut.loadTrainAndTest("TestCaseBase.cb", 1000, 7);
//        
//        List<TestingTrainingPair> loo = l.getTestingAndTrainingSets();
//        for (int i = 0; i < 1000 - DATA_SIZE; i++){
//        	loo.get(0).getTesting().removeCurrentCase(0);
//        }
//        
//        Case mostRecent = loo.get(0).getTesting().getCurrentCase();
//        BestRunReasoning bk = new BestRunReasoning(loo.get(0).getTraining(), 4);
//        bk.setCurrentRun(loo.get(0).getTesting());
//        JLOAFLogger.getInstance().addObserver(this);
//        bk.selectAction(mostRecent.getInput());
//	}
//
//	@Override
//	public void update(Observable arg0, Object arg1) {
//		if (!(arg1 instanceof JLOAFLoggerInfoBundle)){
//			return;
//		}
//		JLOAFLoggerInfoBundle b = (JLOAFLoggerInfoBundle)arg1;
//		
//		if (b.getTag().equals("C")){
//			if (b.getMessage().equals("ALL")){
//				this.allData = new ArrayList<Series<String, Number>>();
//				this.current = new Series<String, Number>();
//			}else if (b.getMessage().equals("RUN")){
//				if (this.current.getData().size() > 0){
//					allData.add(this.current);
//				}
//				this.current = new Series<String, Number>();
//				this.current.setName("RUN : " + this.allData.size());
//			}else if (b.getMessage().equals("DONE")){
//				lineChart.getData().clear();
//				lineChart.getData().add(weights);
//				lineChart.getData().addAll(allData);
//			}
//		}else if (b.getTag().startsWith("S")){
//			if (b.getTag().contains(".5")){
//				if (SHOW_TYPE != Show.STATE){
//					this.current.getData().add(new Data<String, Number>(b.getTag().substring(1), Double.parseDouble(b.getMessage())));
//				}
//			}else{
//				if (SHOW_TYPE != Show.ACTION){
//					this.current.getData().add(new Data<String, Number>(b.getTag().substring(1), Double.parseDouble(b.getMessage())));
//				}
//			}
//			
//			
//		}
////		System.out.println("Tag : " + b.getTag() + ", MSG : " + b.getMessage());
//	}
//	
//	public static void main(String[] args) {
//        launch(args);
//    }
//}