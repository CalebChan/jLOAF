//package org.jLOAF.util;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import javax.imageio.ImageIO;
//
//import org.jLOAF.action.Action;
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
//import javafx.application.Application;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart.Data;
//import javafx.scene.chart.XYChart.Series;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.image.WritableImage;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import java.util.Scanner;
//
//public class CandidateRunTotalView extends Application implements Observer{
//
//	private LineChart<Number, Number> lineChart;
//	
//	private Series<Number, Number> current;
//	
//	private ArrayList<Series<Number, Number>> allData;
//	
//	private Series<Number, Number> weights;
//	
//	private Series<Number, Number> min;
//	
//	private static final int DATA_SIZE = 20;
//	private static final Show SHOW_TYPE = Show.ALL; // 0 = all, 1 = action, 2 = state
//	
//	private int LOCATION = 976;
//	
//	enum Show{
//		ALL,
//		ACTION,
//		STATE
//	};
//	
//	String[] traceFiles={"FixedSequenceAgent.trace","SmartRandomAgent.trace", 
//			"SmartRandomExplorerAgent.trace", "SmartStraightLineAgent.trace",
//			"ZigzagAgent.trace"};
//	
//	String[] weightFunctions = {"DecayWeightFunction.java","FixedWeightFunction.java",
//								"GaussianWeightFunction.java", "LinearWeightFunction.java",};
//// TimeVaryingWeightFunction not included as constructor has not been initialized
//	
//	
//	public CandidateRunTotalView(){
//		this.allData = new ArrayList<Series<Number, Number>>();
//	}
//	
//	@Override
//	public void start(final Stage stage) throws Exception {
//		stage.setTitle("Line Chart Sample");
//		final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis(-DATA_SIZE * 2 + 1, 2, 1);
//        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
//        BorderPane pane = new BorderPane();
//        pane.setCenter(lineChart);
//        Scene scene  = new Scene(pane,800,600); 
//        stage.setScene(scene);
//        stage.show();
//        
//        
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
//		//WeightFunction f = new FixedWeightFunction(1);
//		//WeightFunction f = new GaussianWeightFunction(1, 1);
//		kNNUtil.setWeightFunction(f);
//		
//		min = new Series<Number, Number>();
//		min.setName("Min");
//		
//		weights = new Series<Number, Number>();
//		for (int i = 0, count = -1; i < DATA_SIZE; i++, count--){
//			if(SHOW_TYPE != Show.ACTION){
//				System.out.println("Value : " + f.getWeightValue(i) + " " + i);
//				weights.getData().add(new Data<Number, Number>(i, f.getWeightValue(i)));
//				min.getData().add(new Data<Number, Number>(i, count));
//			}
//			if (i + 0.5 < (DATA_SIZE - 1) && (SHOW_TYPE != Show.STATE)){
//				weights.getData().add(new Data<Number, Number>((i + 0.5), f.getWeightValue(i)));
//				min.getData().add(new Data<Number, Number>((i + 0.5), --count));
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
//		
//		//String fileLocal = "C:/Users/calebchan/Desktop/Stuff/workspace/Test Data/Batch Test 3/TB/Expert/Run 1/FixedSequenceAgent.trace";
//        //String fileLocal = "C:/Users/calebchan/Desktop/Stuff/workspace/Test Data/Batch Test 3/TB/Expert/Run 1/SmartRandomExplorerAgent.trace";
//		
////		String fileLocal = "/home/labadmin/Documents/Run 1/SmartRandomAgent.trace";
//		// for work computer testing
//		
//		String fileLocal = "/home/labadmin/Documents/Run 1/" + traceFiles[inputTrace];
//		// for work computer
//		
////		String fileLocal = "C:/Users/calebchan/Desktop/Stuff/workspace/Test Data/Batch Test 3/TB/Expert/Run 1/" + traceFiles[inputTrace];
//		// for Caleb's home computer
//		
//        
//        LfOTraceParser parser = new LfOTraceParser(fileLocal);
//        
//        if (!parser.parseFile()){
//        	throw new RuntimeException("Failed to Parse");
//        }
//        parser.saveCaseBase("TestCaseBaseTest.cb");
//        
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent arg0) {
//				if (arg0.getCode().equals(KeyCode.LEFT)){
//					LOCATION--;
//					if (LOCATION < 0){
//						LOCATION = 0;
//					}
//					testAction();
//				}else if (arg0.getCode().equals(KeyCode.RIGHT)){
//					LOCATION++;
//					if (LOCATION >= 1000){
//						LOCATION = 999;
//					}
//					testAction();
//				}
//				System.out.println("Key Pressed : " + arg0.getCode().toString());
//			}
//        });
//        testAction();
//        
//        MenuBar menuBar = new MenuBar();
//        
//        // --- Menu File
//        Menu menuFile = new Menu("File");
//
//        MenuItem export = new MenuItem("Export");
//        
//        export.setOnAction(new EventHandler<ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				FileChooser fileChooser = new FileChooser();
//				fileChooser.getExtensionFilters().addAll(
//						new FileChooser.ExtensionFilter("PNG", "*.png"),
//		                new FileChooser.ExtensionFilter("All Images", "*.*")
//		                
//		            );
//				File selectedFile = fileChooser.showSaveDialog(stage);
//				
//				
//				if (selectedFile != null) {
//					lineChart.setAnimated(false);
//					
//					WritableImage snapShot = lineChart.snapshot(null, null);
//
//		            try {
//						ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", selectedFile);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					
//					lineChart.setAnimated(true);
//				}
//			}
//        	
//        });
//        
//        menuFile.getItems().add(export);
//        
//        menuBar.getMenus().addAll(menuFile);
//        
//        pane.setTop(menuBar);
//	}
//	
//	private void testAction(){
//		LeaveOneOut l = LeaveOneOut.loadTrainAndTest("TestCaseBaseTest.cb", 1000, 7);
//		List<TestingTrainingPair> loo = l.getTestingAndTrainingSets();
//        for (int i = 0; i < LOCATION; i++){
//        	loo.get(0).getTesting().removeCurrentCase(0);
//        }
//		
//		Case mostRecent = loo.get(0).getTesting().getCurrentCase();
//        BestRunReasoning bk = new BestRunReasoning(loo.get(0).getTraining(), 5);
//		//KNNBacktracking bk = new KNNBacktracking(loo.get(0).getTraining(), null, 5);
//        bk.setCurrentRun(loo.get(0).getTesting());
//        JLOAFLogger.getInstance().addObserver(this);
//        Action a = bk.selectAction(mostRecent.getInput());
//        System.out.println("Action Equal : " + a.equals(mostRecent.getAction()) + " -> " + a.toString() + " = " + mostRecent.getAction().toString());
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
//				this.allData = new ArrayList<Series<Number, Number>>();
//				this.current = new Series<Number, Number>();
//			}else if (b.getMessage().equals("RUN")){
//				if (this.current.getData().size() > 0){
//					allData.add(this.current);
//				}
//				this.current = new Series<Number, Number>();
//				Action a = (Action)b.getMessageExtra();
//				this.current.setName("RUN : " + this.allData.size() + "--" + a.getName());
//			}else if (b.getMessage().equals("DONE")){
//				allData.add(this.current);
//				lineChart.getData().clear();
//				lineChart.getData().add(weights);
//				lineChart.getData().addAll(allData);
//				lineChart.getData().add(min);
//			}
//		}else if (b.getTag().startsWith("T")){
//			if (b.getTag().contains(".5")){
//				if (SHOW_TYPE != Show.STATE){
//					this.current.getData().add(new Data<Number, Number>(Float.parseFloat(b.getTag().substring(1)), Double.parseDouble(b.getMessage())));
//				}
//			}else{
//				if (SHOW_TYPE != Show.ACTION){
//					this.current.getData().add(new Data<Number, Number>(Float.parseFloat(b.getTag().substring(1)), Double.parseDouble(b.getMessage())));
//				}
//			}
//		}
////		System.out.println("Tag : " + b.getTag() + ", MSG : " + b.getMessage());
//	}
//	
//	
//	public static void main(String[] args) {
//        launch(args);
//    }
//
//}
