package proj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;

public class TesterFrame extends Observable{
	public static final int WIDTH = 900, HEIGHT = 500; // the width and height of the frame
	/*
	 * showSquaresAndRectangles will display information about all the squares and rectangles.
	 * Right now squares have not been implemented.
	 * aFrameToDisplayBigCircles displays circles whose areas exceed 5000.00
	 */
	OutputFrame showSquaresAndRectangles; 
	FrameShowingBigCircles aFrameToDisplayBigCircles;
    /* 
     * myShapes is an array of all the shapes (circles, rectangles ) that we have
     * In this version we will handle only a maximum of 20 shapes.
     */
	
	Shape currentShape;
	ShapeList myShapes;
	ShapeList.ShapeIterator iterator;
	
	private ArrayList<Shape> compoundList;
	
	int currentPhase;
	Color currentColor = Color.black;
	/*  
	 * numShapes is the number of shapes the frame is dealing with.
	 * currentPhase depicts the current situation as follows:
	 *   currentPhase = 0, means the user may move any of the shapes
	 *                = 1, a new compound shape is being defined that contains a number of other shapes 
	 *                     (circles, rectangles or other compound shapes- when you implement compound shapes). 
	 *                     Most of the code  for this phase is not included in the current class definition. It is your 
	 *                     responsibility to develop that code. The code supplied now simply allows the users to specify the 
	 *                     shapes (circles, rectangles for now) to be included in the compound shape.
	 */

	
	/* 
	 * The frame includes the following components:
	 * 		myPanel - the panel where all the shapes (circles, rectangles for now) are displayed. Later on, squares and compound shapes
	 *                will be included) 
	 *                
	 *      shapeButtonPanel -  contains the buttons to specify what type of new shape to create
	 *                (circles, rectangles, squares and compound shapes,
	 *      colorChooserPanel - contains the radiobuttons - blackbutton, redButton, greenButton, blueButton
	 *      
	 *      messageArea - an area for messages to the user 
	 *      inputArea - place for users to supply information about the shapes (e.g., height and width for rectangle, diameter for circles).
	 *      	
	 */
	EditorPanel myPanel; 
	JPanel shapeButtonPanel, colorChooserPanel;
	JTextField messageArea;
	ButtonGroup radioButtonGroupForChoosingColor;
	JRadioButton redButton, greenButton, blueButton, blackButton;
	JTextField inputArea;
	JFrame jFrame;
	/*
	 * redisplay has the following responsibilities:
	 * 		a) display in showSquaresAndRectangles details about rectangles and squares
	 *      b) display in showBigCircle details about circles that have an area > 5000
	 */
	
	public void redisplay(){
		setChanged();
		notifyObservers(iterator);
		myPanel.repaint();
		
		return;
	}
	
	/*
	 * paintComponent is called when repaint is invoked.
	 * paintComponents simply displays all shapes. Hint : use polymorphism to simplify the code 
	 * 
	 */

	@SuppressWarnings("serial")
	private class EditorPanel extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			iterator.first();
			while( !iterator.isDone() ){
				( iterator.currentItem() ).showMe( g );
				
				iterator.next();
			}
		}
	}

	private EditorPanel buildEditorPanel(){
		EditorPanel myPanel;
		myPanel = new EditorPanel();

		myPanel.addMouseListener(new MouseAdapter(){
			/* 
			 * mousePressed is important for selecting a shape for dragging it (currentPhase = 0) and for
			 *              selecting a parameter of a shape to define a compound shape (currentPhase = 5).
			 * 
			 * mouseReleased is important to denote that the operation of dragging a shape is over.
			 */
			public void mouseReleased(MouseEvent e){
				/* when the mouse button is released reset the flags indicating that shape(s) or 
				 * edge(s) has been selected and restore color since the drag operation or the modify 
				 * operation is over.*/
				iterator.first();
				while( !iterator.isDone() ){
					( iterator.currentItem() ).resetShapeSelected();
					
					iterator.next();
				}
			
			}
			/* 
			 * If a shape is selected by pressing mouse button inside the shape, 
			 * save the place where the user pressed mouse button sets the flag 
             * denoting that the shape is selected.
			 * If we are defining compound shape i.e., currentPhase == 1, we select a
			 * shape by pressing the mouse button very close to the parameter.
			 * The color of the shape is temporarily changed to yellow. 
			 */
			public void mousePressed(MouseEvent e){
				int x_value, y_value;
				Coordinates currentMousePosition;
				x_value = e.getX(); // Find the coordinates of the position where the user pressed the mouse button
				y_value = e.getY();
				currentMousePosition = new Coordinates(x_value, y_value);
				
				if (currentPhase == 0){
					iterator.first();
					while( !iterator.isDone() ){
						( iterator.currentItem() ).shapeIsSelected(currentMousePosition);
						
						iterator.next();
					}
				}
				else {
					iterator.first();
					while( !iterator.isDone() ){
						if( ( iterator.currentItem() ).onThePerimeter( currentMousePosition ) ){
							if(compoundList.contains(iterator.currentItem())){
								System.out.println("removing current shape");
								compoundList.remove( iterator.currentItem());
								( iterator.currentItem() ).changeColorBack();
							}
							else{
								System.out.println("add current shape");
								compoundList.add(iterator.currentItem());
								( iterator.currentItem() ).changeColorTemporarily();
							}
						}
						iterator.next();
					}
				}		
				redisplay();
			}   	                      
		});

		myPanel.addMouseMotionListener(new MouseMotionAdapter(){
			/* 
			 * if the mouse is dragged when currentPhase is 0, the selected shapes move 
			 * with the mouse, using method moveShape.
			 */
			public void mouseDragged(MouseEvent e){				
				iterator.first();
				while( !iterator.isDone() ){
					if ( ( iterator.currentItem() ).shapeIsSelected() )
						( iterator.currentItem() ). moveShape( new Coordinates( e.getX(), e.getY() ) );
					
					iterator.next();
				}
					
				redisplay();   
			} 
		});

		myPanel.setBackground(Color.WHITE);
		return myPanel;
	}

    /*
     * Create each of the buttons squareButton, rectangleButton, circleButton, compoundFigureButton
     * and define the event handler for ActionEvent.
     * 
     * When button COMPOUND is pressed for the first time currentPhase becomes 1 and the user can 
     * specify the shapes (currently rectangle or circle) to be glued together
     * 
     * When button COMPOUND is pressed for the second time, normal operation resumes again.
     * This is currently done by setting currentPhase to 0. Hint: You have to include code to 
     * create the compound shape, later on.
     * 
     */
	private JPanel buildShapeChooserPanel(){
		JPanel buttonPanel;
		JButton squareButton, rectangleButton, circleButton, 
		        compoundFigureButton;

		buttonPanel = new JPanel();
		
		compoundFigureButton = new JButton("COMPOUND");
		compoundFigureButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (currentPhase == 0){
					currentPhase = 1;
					compoundList = new ArrayList<Shape>(100);
					
					iterator = myShapes.createIterator(100);
					
					messageArea.setText("select figures to glue then press COMPOUND button again");
				} else{ 
					currentPhase = 0;
					
					currentShape = new CompoundShape();
					
					

					
					for(Shape shape: compoundList){
						(( CompoundShape )currentShape ).addShape(shape);
							myShapes.removeShape(shape);
					}
					
					myShapes.append( currentShape );
					iterator = myShapes.createIterator(100);
					
					messageArea.setText("");
					redisplay();
					currentShape.changeColorBack();
				}		
			}
		}
		);
		
		buttonPanel.add(compoundFigureButton);
		squareButton = new JButton("Create SQUARE");
		squareButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				messageArea.setText("Square size?");
			    currentShape = new Square(currentColor);
				myShapes.append( ( (Shape)currentShape ) );

				iterator = myShapes.createIterator(100);
				redisplay();
			}
		}
		);
		
		buttonPanel.add(squareButton);

		rectangleButton = new JButton("Create RECTANGLE");
		rectangleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				    messageArea.setText("Rectangle width?");
				    currentShape = new Rectangle(currentColor);
					myShapes.append( ( (Shape)currentShape ) );

					iterator = myShapes.createIterator(100);
					redisplay();
			}
		}
		);

		buttonPanel.add(rectangleButton);

		circleButton = new JButton("Create CIRCLE");
		circleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					currentShape = new Circle(currentColor);
					myShapes.append( ( (Shape)currentShape) );

					iterator = myShapes.createIterator(100);
					messageArea.setText("Circle Diameter?");
					redisplay();
			}
		}
		);
		
		buttonPanel.add(circleButton);
		
		messageArea = new JTextField(20);
		buttonPanel.add(messageArea);
		
		inputArea = new JTextField(3);
		inputArea.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int width, height, diameter;
				if (messageArea.getText().equals("Rectangle width?")){
					if (!(inputArea.getText().equals(""))){
						width = Integer.parseInt(inputArea.getText());
						((Rectangle) currentShape).changeWidth(width);
					}
					messageArea.setText("Rectangle height?");
					inputArea.setText("");
					redisplay();
				} else if (messageArea.getText().equals("Rectangle height?")){
					if (!(inputArea.getText().equals(""))){
						height = Integer.parseInt(inputArea.getText());
						((Rectangle) currentShape).changeHeight(height);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				} else if (messageArea.getText().equals("Square size?")){
					if (!(inputArea.getText().equals(""))){
						height = Integer.parseInt(inputArea.getText());
						((Square) currentShape).changeSize(height);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				} else if (messageArea.getText().equals("Circle Diameter?")){
					if (!(inputArea.getText().equals(""))){
						diameter = Integer.parseInt(inputArea.getText());
						((Circle) currentShape).changeDiameter(diameter);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				}	
			} 
		}
		);
		
		buttonPanel.add(inputArea);
		
		return buttonPanel;
	} 
	
	/*
	 * buildColorChooserPanel included 3 radio buttons so that users can select red, blue or green
	 * in addition to the original black color for the newly created shape. The user can select 
	 * one of these buttons to change the color for the newly created shape.
	 * We have used a  straight-forward anonymous handler for events in each radio button.
	 */

	private JPanel buildColorChooserPanel(){
		JPanel radioButtonPanel;
		

		radioButtonPanel = new JPanel();
		blackButton = new JRadioButton("BLACK");
		blackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.black;
			}
		}
		);
		
		redButton = new JRadioButton("RED");
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.red;
			}
		}
		);

		greenButton = new JRadioButton("GREEN");
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.green;
			}
		}
		);

		blueButton = new JRadioButton("BLUE");
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.blue;
			}
		}
		);

		radioButtonGroupForChoosingColor = new ButtonGroup();

		radioButtonGroupForChoosingColor.add(blueButton);
		radioButtonGroupForChoosingColor.add(greenButton);
		radioButtonGroupForChoosingColor.add(redButton);
		
		radioButtonPanel.add(blackButton);
		radioButtonPanel.add(blueButton);
		radioButtonPanel.add(greenButton);
		radioButtonPanel.add(redButton);
		
		return radioButtonPanel;
	}

	public TesterFrame(){	
		showSquaresAndRectangles = new OutputFrame();
		showSquaresAndRectangles.setLocation(0, 500);
		
		aFrameToDisplayBigCircles = new FrameShowingBigCircles();
		aFrameToDisplayBigCircles.setLocation(900, 0);
		
		this.addObserver(this.showSquaresAndRectangles); 
		this.addObserver(this.aFrameToDisplayBigCircles);
		
		compoundList = new ArrayList<Shape>(100);
		myShapes = new ShapeList();
		iterator = myShapes.createIterator(100);
		
		currentPhase = 0;
		myPanel = buildEditorPanel();
		shapeButtonPanel = buildShapeChooserPanel();
		colorChooserPanel = buildColorChooserPanel();
		
		jFrame = new JFrame("Tester Frame");

		jFrame.add(colorChooserPanel, BorderLayout.NORTH);
		jFrame.add(shapeButtonPanel, BorderLayout.SOUTH);
		jFrame.add(myPanel, BorderLayout.CENTER);
		jFrame.setSize(WIDTH, HEIGHT);
		jFrame.setVisible(true);
	}

	public static void main(String a[]){
		TesterFrame aFrame = new TesterFrame();
		
		return;
	}
}