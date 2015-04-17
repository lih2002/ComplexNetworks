package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

//import application.immuine.NetworkImmuine;

import javax.swing.*;

import java.util.Vector;
import java.util.ArrayList;
import network.*;
import linkPrediction.*;
import properties.Partitions;
import tools.*;
import community.*;
import model.*;
import properties.*;
import io.*;
import spectrum.*;
//import edu.uci.ics.jung.graph.*;
import javax.swing.Action;
import java.beans.PropertyChangeListener;
import java.lang.Object;
import java.lang.String;
import java.util.*;
import application.*;

public class WinMain {

	private JTextField textFieldCommModelInitSubcommSize;
	private JTextField textFieldCommModelInitCommSize;
	private JTextField textFieldFixedS;
	private JTextField textFieldEndTao;
	private JTextField textFieldTaoInterval;
	private JTextField textFieldStartTao;
	private JTextField textFieldSpreadSaveInterval;
	private JTextField textFieldRecoveryRate;
	private JTextField textFieldSpreadRepeat;
	private JTextField textFieldRewireRepeatNum;
	private JTextField textFieldEpidemicRate;
	private JTextField textFieldInitialInfectiousRate;
	private JTextField textFieldCBSetB;
	private JTextField textFieldCBSetA;
	private JTextField textFieldBADegree;
	private JTextField textFieldBANetSize;
	private JTextField textFieldERP;
	private JTextField textFieldERNetSize;
	private JTextField textFieldColCount;
	private JTextField textFieldRowCount;
	private JTextField textFieldLastFilePath;
	private JComboBox textFieldLoadFilter;
	private JTextField TextFieldSavePath;
	private JList loadFilelist;
	private JFrame frame;
	private JCheckBox copyLoadDirectoryCheckBox;
	private Vector<String> fileList;
	private JButton jButtonCommunityEvolve = null;
	private JButton jButtonIPV6Data = null;
	private JButton jButtonDelNodeWithDegree = null;
	private JButton jButtonNgbDegrees = null;
	private JButton jButtonRichClub = null;
	private JButton jButtonCCDF = null;
	private JTextField jTextFieldPostfix = null;
	private JButton jButtonConvertMRWCommunityData = null;


	/**
	 * This method initializes jButtonCommunityEvolve	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCommunityEvolve() {
		if (jButtonCommunityEvolve == null) {
			jButtonCommunityEvolve = new JButton();
			jButtonCommunityEvolve.setAction(new Action(){

	public void addPropertyChangeListener(PropertyChangeListener arg0) {
	}

	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
		
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
		
	}

	public void putValue(String arg0, Object arg1) {
	}

	public void removePropertyChangeListener(PropertyChangeListener arg0) {
	}

	public void setEnabled(boolean arg0) {
	}

	public void actionPerformed(ActionEvent arg0) {
		
		String[] networkFiles = getSelectedFiles();
		for (int i = 0; i < networkFiles.length-1; i++) {

			// first, load networks
			GeneralNetwork netOld = new GeneralNetwork();
			NetworkLoader.loadDataFromOneFile(netOld,
					networkFiles[i]);
			GeneralNetwork netNew = new GeneralNetwork();
			NetworkLoader.loadDataFromOneFile(netNew,
					networkFiles[i+1]);
			
			//Debug.outn(netOld.getNetworkSize()+" "+netNew.getNetworkSize());
//			Set<Integer> birthNode =NetworkAnalysis.birthNodes(netOld, netNew);
//			Debug.outn("birth node num "+birthNode.size());
//			GeneralCommunity generalCommunity = new GeneralCommunity();
//					
//			CommunityBuilder.loadCommunityFromFile(generalCommunity,
//					new File(networkFiles[i+1]).getPath(), "",
//					".cnm.community");
//			CommunityAnalysis.evolveAnalysis(netNew, generalCommunity, birthNode,
//					TextFieldSavePath.getText()	+ "//","community.evl",true);
//		
			
		}
		
		
		
	}});
			jButtonCommunityEvolve.setText("eveolve");
		}
		return jButtonCommunityEvolve;
	}

	/**
	 * This method initializes jButtonIPV6Data	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonIPV6Data() {
		if (jButtonIPV6Data == null) {
			jButtonIPV6Data = new JButton();
			jButtonIPV6Data.setText("Convert IPV6data");
			jButtonIPV6Data.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String[] networkFiles = getSelectedFiles();
					
					for (int i = 0; i < networkFiles.length; i++) {
						
	
					CAIDARawData.convertIPV6( networkFiles[i], 
								"", TextFieldSavePath.getText() + "//");
						
					}
				}
			});
		}
		return jButtonIPV6Data;
	}

	/**
	 * This method initializes jButtonDelNodeWithDegree	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDelNodeWithDegree() {
		if (jButtonDelNodeWithDegree == null) {
			jButtonDelNodeWithDegree = new JButton();
			jButtonDelNodeWithDegree.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String[] networkFiles = getSelectedFiles();
					
					for (int i = 0; i < networkFiles.length; i++) {			
						GeneralNetwork generalNet = new GeneralNetwork();
						NetworkLoader.loadDataFromOneFile(generalNet,
								networkFiles[i]);
						Reconstruct.deleteNode(generalNet, 3, TextFieldSavePath.getText() + "//", "", "");						
						
					}
					
				}
			});
		}
		return jButtonDelNodeWithDegree;
	}

	/**
	 * This method initializes jButtonNgbDegrees	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNgbDegrees() {
		if (jButtonNgbDegrees == null) {
			jButtonNgbDegrees = new JButton();
			jButtonNgbDegrees.setText("ngb degree");
			jButtonNgbDegrees.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String[] networkFiles = getSelectedFiles();
					
					for (int i = 0; i < networkFiles.length; i++) {			
						GeneralNetwork generalNet = new GeneralNetwork();
						NetworkLoader.loadDataFromOneFile(generalNet,
								networkFiles[i]);
						//float avgD = ConnectionPpreferce.maTaiCoefficient(generalNet, 4);
					//	Debug.outn(generalNet.getNetworkID()+" "+avgD);					
						
					}
					
				}
			});
		}
		return jButtonNgbDegrees;
	}

	/**
	 * This method initializes jButtonRichClub	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRichClub() {
		if (jButtonRichClub == null) {
			jButtonRichClub = new JButton();
			jButtonRichClub.setText("Rich Club");
			jButtonRichClub.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					
					String[] networkFiles = getSelectedFiles();
					
//					for (int i = 0; i < networkFiles.length; i++) {			
//						GeneralNetwork generalNet = new GeneralNetwork();
//						NetworkLoader.loadDataFromOneFile(generalNet,
//								networkFiles[i]);
//						double r = RichClub.richClubCoefficient(generalNet, 0.002);
//						Debug.outn(r);					
//						
//					}
				
				}
			});
		}
		return jButtonRichClub;
	}

	/**
	 * This method initializes jButtonCCDF	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonCCDF() {
		if (jButtonCCDF == null) {
			jButtonCCDF = new JButton();
			jButtonCCDF.setText("PDF");
			jButtonCCDF.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
								
					String[] networkFiles = getSelectedFiles();
					
					for (int i = 0; i < networkFiles.length; i++) {			
						GeneralNetwork generalNet = new GeneralNetwork();
						NetworkLoader.loadDataFromOneFile(generalNet,
								networkFiles[i]);
						double [][]ccdf = DegreeDistribution.percentPDF(generalNet);				
						Debug.outn(generalNet.getNetID()) ;
						for(int m=0;m<ccdf.length;m++){
							Debug.outn(ccdf[m][0]+"\t"+ccdf[m][1]);							
						}
					}
				
				}
			});
		}
		return jButtonCCDF;
	}

	/**
	 * This method initializes jTextFieldPostfix	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldPostfix() {
		if (jTextFieldPostfix == null) {
			jTextFieldPostfix = new JTextField();
			jTextFieldPostfix.setBackground(Color.yellow);
			jTextFieldPostfix.setPreferredSize(new Dimension(200, 20));
		}
		return jTextFieldPostfix;
	}

	/**
	 * This method initializes jButtonConvertMRWCommunityData	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonConvertMRWCommunityData() {
		if (jButtonConvertMRWCommunityData == null) {
			jButtonConvertMRWCommunityData = new JButton();
			jButtonConvertMRWCommunityData.setText("convert MRW community data");
			jButtonConvertMRWCommunityData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

						
							String[] networkFiles = getSelectedFiles();
							
							for (int i = 0; i < networkFiles.length; i++) {		
						
							CommunityTools.convertCommnityData( new File(networkFiles[i]).getAbsolutePath(), 
									 new File(networkFiles[i]).getName(), "", 
									TextFieldSavePath.getText() + "//", "", ".MRW.community");
							
							}
							
						
						}
					});
		}
		return jButtonConvertMRWCommunityData;
	}

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			WinMain window = new WinMain();
		
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public WinMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {

		fileList = new Vector();

		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(1, 0));
		frame.setSize(995, 555);
		frame.setTitle("complex networks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		splitPane.setLastDividerLocation(3);
		splitPane.setDividerSize(9);
		splitPane.setName("splitPane");
		frame.getContentPane().add(splitPane);

		final JPanel panel = new JPanel();
		final GridLayout gridLayout_1 = new GridLayout(0, 1);
		gridLayout_1.setVgap(10);
		panel.setLayout(gridLayout_1);
		panel.setAutoscrolls(true);
		splitPane.setLeftComponent(panel);
		
		final JCheckBox saveSpreadNetCheckBox = new JCheckBox();
		saveSpreadNetCheckBox.setText("save Pajek");

		JScrollPane scrollPane;
		// list.setModel();

		final JPanel panel_1 = new JPanel();
		panel_1.setAutoscrolls(true);
		panel_1.setLayout(new BorderLayout());
		splitPane.setRightComponent(panel_1);

		final JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setName("splitPane_1");
		panel_1.add(splitPane_1);

		final JTabbedPane tabbedPane = new JTabbedPane();
		splitPane_1.setRightComponent(tabbedPane);

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(new CardLayout());
		tabbedPane.addTab("Network Analysis", null, panel_3, null);

		final JTabbedPane tabbedPane_1 = new JTabbedPane();
		tabbedPane_1.setName("tabbedPane_1");
		panel_3.add(tabbedPane_1, tabbedPane_1.getName());

		final JPanel panel_6 = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_6.setLayout(flowLayout);
		tabbedPane_1.addTab("Basic", null, panel_6, null);

		final JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(Color.black, 1, false));
		panel_6.add(panel_7);

		final JButton getDegreesButton = new JButton();
		panel_7.add(getDegreesButton);
		getDegreesButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
						
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork gelNet = new GeneralNetwork();
					gelNet.loadNetworkData(	networkFiles[i]);
					TestNetwork.dump(gelNet);
//					new NetworkAnalysis().saveDegrees(gelNet,
//							TextFieldSavePath.getText() + "//", gelNet
//									.getNetworkID().split("\\.")[0], "");
//					int[] allDegree  = gelNet.getAllNodeDegrees();
//					double avgDeg = MathTool.average(allDegree);
//					double varDeg = MathTool.stdDeviation(allDegree);
//					Debug.outn(varDeg*varDeg/avgDeg);
				}
			}

		});
		getDegreesButton.setText("get degrees");

		final JPanel panelGetNetProperties = new JPanel();
		panel_6.add(panelGetNetProperties);
		panelGetNetProperties.setBorder(new BevelBorder(BevelBorder.LOWERED));

		final JButton getNetworkPropertiesButton = new JButton();
		getNetworkPropertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork generalNet = new GeneralNetwork();
					generalNet.loadNetworkData(networkFiles[i]);
					new NetworkAnalysis().saveNetPropertiesToFile(generalNet,
							TextFieldSavePath.getText() + "//", "", "");
				}

			}
		});
		panelGetNetProperties.add(getNetworkPropertiesButton);
		getNetworkPropertiesButton.setText("get network properties");
		
		final JCheckBox connectedCheckBox = new JCheckBox();
		connectedCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			}
		});
		connectedCheckBox.setText("connected");

		final JCheckBox nodeNumberCheckBox = new JCheckBox();
		nodeNumberCheckBox.setText("node number");
		panelGetNetProperties.add(nodeNumberCheckBox);

		final JCheckBox edgeNumberCheckBox = new JCheckBox();
		edgeNumberCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			}
		});
		edgeNumberCheckBox.setText("edge number");
		panelGetNetProperties.add(edgeNumberCheckBox);

		final JCheckBox checkBox_2 = new JCheckBox();
		checkBox_2.setText("New JCheckBox");
		panelGetNetProperties.add(checkBox_2);

		final JCheckBox checkBox_3 = new JCheckBox();
		checkBox_3.setText("New JCheckBox");
		panelGetNetProperties.add(checkBox_3);

		final JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(Color.black, 1, false));
		panel_6.add(panel_10);

		final JButton getCorrelationButton = new JButton();
		getCorrelationButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					CommunityNetwork commNet = new CommunityNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					// commNet.degreeDegreeCorrelation();
					// commNet.nodeLinkWNodeLinkWCorrelation();
					// commNet.nodeNodeWNodeNodeWCorrelation();
					// commNet.linkWeightCorrelation();
			//		commNet.getNNWNLW();
					
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + "commNetCorrelation", commNet.getAllCorrelation(true), true);
					// Debug.outn(commNet.getAllCorrelation());
				}

			}
		});
		panel_10.add(getCorrelationButton);
		getCorrelationButton.setText("community net correlation");

		final JButton correlationButton = new JButton();
		correlationButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);

					String fileName = geNet.getNetID().split("\\.")[0]
							+ "." +
							// geNet.getNetworkID().split("\\.")[2]+
							".degCor";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + fileName, Correlation
							.degreeCorrelation(geNet)
							+ "\n", true);
					// geNet.getNetworkID().split("\\.")[1]+" "+
					Debug.outn(geNet.getNetID() + "\t"
							+ Correlation.degreeCorrelation(geNet));
				}
			}
		});
		correlationButton.setText("correlation");
		panel_10.add(correlationButton);

		final JButton valuetoonefileButton = new JButton();
		valuetoonefileButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new NetworkAnalysis().getValueToOneFile(getSelectedFiles(),
						".d", TextFieldSavePath.getText() + "//", new File(
								getSelectedFiles()[0]).getName()
								+ ".allInOne");
			}
		});
		valuetoonefileButton.setText("valueToOneFile");
		panel_6.add(valuetoonefileButton);

		final JButton convertToPajekButton = new JButton();
		convertToPajekButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
//					RawData.formatToPajekNet(commNet, TextFieldSavePath
//							.getText()
//							+ "//", "", "");
				}

			}
		});

		convertToPajekButton
				.setText("convert  general network to Pajek format");
		panel_6.add(convertToPajekButton);

		final JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(Color.black, 1, false));
		panel_6.add(panel_8);

		final JButton convertToCommunityButton = new JButton();
		panel_8.add(convertToCommunityButton);
		convertToCommunityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
//					new NetworkAnalysis().network2Community(commNet,
//							TextFieldSavePath.getText() + "//", "",
//							".cnm.community");
				}
			}
		});
		convertToCommunityButton.setText("convert to community");

		final JButton intersectionNodesButton = new JButton();
		intersectionNodesButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				NetworkAnalysis NA = new NetworkAnalysis();
				// String[] networkFiles = getSelectedFiles();
				NA.getIntersectionNet(networkFiles, TextFieldSavePath.getText()
						+ "//", "", ".coreNet");
				// Debug.outn( NA.getIntersectionNodeIDs(networkFiles).size());

			}
		});
		intersectionNodesButton.setText("intersection network");
		panel_6.add(intersectionNodesButton);

		final JButton getIntersectionNodeButton = new JButton();
		getIntersectionNodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				NetworkAnalysis NA = new NetworkAnalysis();
				// String[] networkFiles = getSelectedFiles();
				NA.getNodeDegreesInNetworks(networkFiles, TextFieldSavePath
						.getText()
						+ "//", "Inet", ".commDegree");

			}
		});
		getIntersectionNodeButton.setText("get intersection node degrees");
		panel_6.add(getIntersectionNodeButton);

		final JButton nodeLifeTimeButton = new JButton();
		nodeLifeTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				NetworkAnalysis NA = new NetworkAnalysis();
				// String[] networkFiles = getSelectedFiles();
				NA.getAvgDegrees(networkFiles, TextFieldSavePath.getText()
						+ "//", "nodeAvgDegree", ".avgD");

			}
		});
		nodeLifeTimeButton.setText("node life time");
		panel_6.add(nodeLifeTimeButton);

		final JButton clusterCoefficientButton = new JButton();
		clusterCoefficientButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				StringBuffer data = new StringBuffer();
				String netID="";
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork generalNet = new GeneralNetwork();
				
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					float ce =new ClusterCoefficient().getNetworkClusterCoefficient(generalNet);
					data.append(generalNet.getNetID()+"\t"+ce+"\n");
					Debug.outn(ce);
					netID = new File(networkFiles[i]).getName();
				}
				
				
				FileOperation.saveStringToFile(TextFieldSavePath.getText()
						+ "//" + netID.split("\\.")[0]+"."+netID.split("\\.")[1] 
						                + ".clusterCoefficient",data.toString() ,
						false);
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel_6.add(btnNewButton_1);
		clusterCoefficientButton.setText("cluster coefficient");
		panel_6.add(clusterCoefficientButton);

		panel_6.add(getJButtonNgbDegrees(), null);
		panel_6.add(getJButtonRichClub(), null);
		panel_6.add(getJButtonCCDF(), null);
		final JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Specturm", null, panel_5, null);

		final JPanel panel_21 = new JPanel();
		panel_21.setBorder(new LineBorder(Color.black, 1, false));
		panel_5.add(panel_21);

		final JButton getAdjacentMatrixButton = new JButton();
		panel_21.add(getAdjacentMatrixButton);
		getAdjacentMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NetworkAnalysis.convertAdjacentMatrix(getSelectedFiles(), ".d",
						TextFieldSavePath.getText() + "//", "", "");
			}
		});
		getAdjacentMatrixButton.setText("get adjacent matrix");

		final JButton getSparseAdjButton = new JButton();
		panel_21.add(getSparseAdjButton);
		getSparseAdjButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					NetMatrix.saveNetworkSparseAdjacentMatrix(commNet,
							TextFieldSavePath.getText() + "//", "", "");

				}

			}
		});
		getSparseAdjButton.setText("get sparse adj mat");

		final JPanel panel_22 = new JPanel();
		panel_22.setBorder(new LineBorder(Color.black, 1, false));
		panel_5.add(panel_22);

		final JButton getLaplacianMatrixButton = new JButton();
		panel_22.add(getLaplacianMatrixButton);
		getLaplacianMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NetworkAnalysis.getLaplacMatrix(getSelectedFiles(),
						TextFieldSavePath.getText() + "//", "", "");
			}
		});
		getLaplacianMatrixButton.setText("get laplacian matrix");

		final JButton getLaplacMatrixButton = new JButton();
		panel_22.add(getLaplacMatrixButton);
		getLaplacMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NetworkAnalysis.getNormalizedLaplacMatrix(getSelectedFiles(),
						TextFieldSavePath.getText() + "//", "", "");
			}
		});
		getLaplacMatrixButton.setText("get normalized laplac matrix");

		final JButton sparseNormalLapButton = new JButton();
		sparseNormalLapButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					NetMatrix
							.saveNetworkSparseNormalizedLaplacianMatrixToFile(
									commNet,
									TextFieldSavePath.getText() + "//", "", "");

				}

			}
		});
		sparseNormalLapButton.setText("sparse normal lap mat");
		panel_22.add(sparseNormalLapButton);

		final JButton sparseLaplacianMatButton = new JButton();
		sparseLaplacianMatButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					NetMatrix.saveNetworkSparseLaplacianMatrixToFile(commNet,
							TextFieldSavePath.getText() + "//", "", "");

				}

			}
		});
		panel_22.add(sparseLaplacianMatButton);
		sparseLaplacianMatButton.setText("sparse laplacian mat");

		final JButton modularityMatrixButton = new JButton();
		modularityMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity gComm = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(gComm,
							new File(networkFiles[i]).getPath(), "",
							".cnm.community");

					
					NetMatrix.saveModularityMatrixToFile(generalNet, gComm,
							TextFieldSavePath.getText() + "//", "", "");

				}

				
			}
		});
		modularityMatrixButton.setText("modularity matrix");
		panel_5.add(modularityMatrixButton);

		final JButton largestPartitionToButton = new JButton();
		largestPartitionToButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork commNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					Partitions.largestPartitionsToNetwork(commNet,
							TextFieldSavePath.getText() + "//", "", "");
				}
			}
		});
		largestPartitionToButton.setText("largest partition to network");
		panel_5.add(largestPartitionToButton);

		final JButton correlationboundButton = new JButton();
		correlationboundButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					String path = "";

					String output = "";
					

					String netID = new File(networkFiles[i]).getName().split(
							"\\.")[0];
					
					Debug.outn(ClusterCoefficient.linkDensity(generalNet));
					// +new File(networkFiles[i]).getName().split("\\.")[1];
					//CorrelationBound.lamd1MBound(generalNet);
//					String bound = "";
//					for (int j = 0; j < bounds.length; j++) {
//						bound = bound + " " + bounds[j];
//						// Debug.outn("bound length"+j);
//
//					}
//
//					Debug.outn(bound);

					// //GeneralNetwork rewiredNet = new GeneralNetwork();
					// //NetworkLoader.loadDataFromOneFile(generalNet,
					// // networkFiles[i]);
					// //Debug.outn(rewiredNet.getNetworkID());
					// //float
					// rndRewCor=Correlation.degreeCorrelation(rewiredNet);
					//					
					// String maxEV =
					// NetMatrix.getLargestEigenValue(networkFiles[i]+".adjMat.adjEV");
					//					
					//					
					// output=output+"
					// "+Correlation.degreeCorrelation(generalNet)+" "+bound+"
					// "+ maxEV+" "
					// +"\n";
					//					
					// Debug.outn(output);
					// FileOperation.saveStringToFile(TextFieldSavePath.getText()
					// + "//"+netID+".corBound"
					// , output, true);
				}// /////////////////////////////////////every file

			}
		});
		correlationboundButton.setText("link density");
		panel_5.add(correlationboundButton);

		final JButton getLargestEigenvalueButton = new JButton();
		getLargestEigenvalueButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					String netID = new File(networkFiles[i]).getName();
					// Debug.out(networkFiles[i]);
					double lE = NetMatrix.getLargestEigenValue(networkFiles[i],false);
					// Debug.out(lE);
					String output = netID + " " + lE + "\n";
					netID = "maxEV";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0] + ".maxEV", output,
							true);
				}

			}
		});
		getLargestEigenvalueButton.setText("get largest eigenValue");
		panel_5.add(getLargestEigenvalueButton);

		final JButton effectiveResistanceButton = new JButton();
		effectiveResistanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					String netID = new File(networkFiles[i]).getName();
					// Debug.out(networkFiles[i]);
					
					double effResis = NetMatrix.getNormalizedEffectiveResistance(networkFiles[i]);
					 Debug.out(effResis);
					String output = netID + "\t" + Double.toString(effResis) + "\n";
					//netID = "maxEV";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0]+"."+netID.split("\\.")[1] + ".effRisis", output,
							true);
				}
			}
		});
		effectiveResistanceButton.setText("effective resistance");
		panel_5.add(effectiveResistanceButton);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					String netID = new File(networkFiles[i]).getName();
					// Debug.out(networkFiles[i]);
					double [] sortedEigV = NetMatrix.getSortedEigenValue(networkFiles[i],true);
					Debug.outn(sortedEigV, "ee");
					double lE = sortedEigV[sortedEigV.length-1];
					// Debug.out(lE);
					String output = netID + "\t" + lE + "\n";
					netID = "degreeCorrelationRewire";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0] + ".spetralRadius", output,
							true);
				}
				
			}
		});
		button_2.setText("sectral radius");
		panel_5.add(button_2);

		final JButton algebraicConnectivityButton = new JButton();
		algebraicConnectivityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				// the second smallest laplacian eigen value
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					String netID = new File(networkFiles[i]).getName();
					// Debug.out(networkFiles[i]);
					double [] sortedEigV = NetMatrix.getSortedEigenValue(networkFiles[i],true);
					double algConnectivity = sortedEigV[1];
					// Debug.out(lE);
					String output = netID + "\t" + algConnectivity + "\n";
					netID = "degreeCorrelationRewire";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0] + ".algConnectivity", output,
							true);
				}
				
				
			}
		});
		algebraicConnectivityButton.setText("algebraic connectivity");
		panel_5.add(algebraicConnectivityButton);

		final JPanel panelCommunityAnalysis = new JPanel();
		panelCommunityAnalysis.setAutoscrolls(true);
		tabbedPane.addTab("Community Analysis", null, panelCommunityAnalysis,
				null);

		final JPanel panel_17 = new JPanel();
		panel_17.setBorder(new LineBorder(Color.black, 1, false));
		panelCommunityAnalysis.add(panel_17);

		final JLabel partitionNetworkLabel = new JLabel();
		partitionNetworkLabel.setText("partition network");
		panel_17.add(partitionNetworkLabel);

		final JPanel panel_16 = new JPanel();
		panel_17.add(panel_16);

		final JButton weakcomponentButton = new JButton();
		panel_16.add(weakcomponentButton);
		weakcomponentButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					CommunityTools.WeakComponentClusterer(generalCommunity,
							generalNet, TextFieldSavePath.getText() + "//", "",
							"");

					Debug.outn("partition finished "
							+ generalNet.getNetID());
				}

			}
		});
		weakcomponentButton.setText("WeakComponent");

		final JPanel panel_15 = new JPanel();
		panel_17.add(panel_15);

		final JButton biocomponetButton = new JButton();
		biocomponetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					CommunityTools.BicomponentClusterer(generalCommunity,
							generalNet, TextFieldSavePath.getText() + "//", "",
							"");

					Debug.outn("partition finished "
							+ generalNet.getNetID());
				}

			}
		});
		biocomponetButton.setText("BioComponent");
		panel_15.add(biocomponetButton);

		final JPanel panel_14 = new JPanel();
		panel_17.add(panel_14);

		final JButton voltageclustererButton = new JButton();
		voltageclustererButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					CommunityTools.VoltageClusterer(generalCommunity,
							generalNet, TextFieldSavePath.getText() + "//", "",
							".vot.community");
					

					Debug.outn("net finished" + generalNet.getNetID());
				}
			}
		});
		panel_14.add(voltageclustererButton);
		voltageclustererButton.setText("VoltageClusterer");

		final JPanel panel_18 = new JPanel();
		panel_17.add(panel_18);

		final JButton betweenessCommunityButton = new JButton();
		panel_18.add(betweenessCommunityButton);
		betweenessCommunityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());
//					CommunityTools.EdgeBetweenness(generalCommunity,
//							generalNet, TextFieldSavePath.getText() + "//", "",
//							".comm");

					// CommunityBuilder.loadCommunityFromFile(generalCommunity,
					// new File(networkFiles[i]).getPath(), "",
					// ".community");
					// CommunityAnalysis.communityToNetwork(generalCommunity,
					// generalNet, TextFieldSavePath.getText() + "//",
					// generalNet.getNetworkID(), ".communityNet");
				}

			}
		});
		betweenessCommunityButton.setText("betweeness community");

		final JPanel panelCommToNetwork = new JPanel();
		panelCommToNetwork.setBorder(new LineBorder(Color.black, 1, false));
		panelCommunityAnalysis.add(panelCommToNetwork);

		final JButton communityToNetButton = new JButton();
		communityToNetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					// first, load networks
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "",
							".MRW.Community");

					// Debug.outn(networkFiles[i]) ;
//					CommunityAnalysis.communityToNetwork(generalCommunity,
//							generalNet, TextFieldSavePath.getText() + "//",
//							generalNet.getNetworkID(), ".MRW.communityNet");
//					Debug.outn(generalCommunity.getCommunityID()+" over ");
				}

			}
		});
		communityToNetButton.setText("community to network");
		panelCommToNetwork.add(communityToNetButton);

		final JButton communityToPajekButton = new JButton();
		panelCommToNetwork.add(communityToPajekButton);
		communityToPajekButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					// first, load networks
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "", jTextFieldPostfix.getText().trim());
//					RawData.formatToPajekNet(generalCommunity, generalNet,
//							TextFieldSavePath.getText() + "//", generalNet
//									.getNetworkID(), ".net");
					// Debug.outn(networkFiles[i]) ;

				}

			}
		});
		communityToPajekButton.setText("Community to pajek");

		final JButton outputModularityButton = new JButton();
		panelCommToNetwork.add(outputModularityButton);
		outputModularityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "", "");
					Debug.outn(generalCommunity.getCommunityID()+"\t"+generalCommunity.getModularity());
//					float avgCommSize = MathTool.average(generalCommunity.getSubCommSize());
//					double stdCommSize = MathTool.stdDeviation(generalCommunity.getSubCommSize());
//					Debug.outn(avgCommSize+" "+stdCommSize);
					String netID = generalCommunity.getCommunityID();
					String output = netID+"\t"+Double.toString(generalCommunity.getModularity())+"\n";
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0]+"."+netID.split("\\.")[1] + ".modularity", 
							output,
							true);

				}

			}
		});
		outputModularityButton.setText("output modularity");

		final JButton communityPropButton = new JButton();
		communityPropButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					
					// first, load networks
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());
					
					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "",
							".cnm.community");
					int []subCommSize = (generalCommunity.getSubCommsSizes());
					
					Debug.outn(generalCommunity.getSubCommsSizes().length);
					for(int m=0;m<subCommSize.length;m++){
						//Debug.out(subCommSize[m]+" ");
					}
					
					
					// Debug.outn(networkFiles[i]) ;
//					int [][]sumDegComm = CommunityAnalysis.communityProp(generalCommunity, generalNet, TextFieldSavePath.getText() + "//",
//							generalNet.getNetworkID(), ".commDegSum", true);
//					//Debug.outn(sumDegComm,"sumDegcomm ");
//					/*c bound of modulariy*/
//					for(int m=0;m<sumDegComm.length;m++){
//						//Debug.outn(sumDegComm[m][1]/generalNet.getnet+"\t"+sumDegComm[m][0]/);
//					}
//					double maxDiff=0;
//					for(int w=0;w<sumDegComm.length;w++){
//						for(int z=w+1;z<sumDegComm.length;z++){
//							int diff = Math.abs(sumDegComm[w]-sumDegComm[z]);
//							if(diff>maxDiff){
//								maxDiff=diff;
//							}
//						}
//					}	
//					double avgSumDegComm = MathTool.average(sumDegComm);
//					Debug.outn(maxDiff);
//					double maxCBound = 2*Math.sqrt(2)*generalNet.getEdgesNum()/maxDiff;
//					Debug.outn(maxCBound);
//					String cB= maxCBound +"\n";
//					FileOperation.saveStringToFile(TextFieldSavePath.getText() + "//"+"rewire.cBound",
//							cB, true);		
				
				}////each network
				
			}
		});
		communityPropButton.setText("community prop");
		panelCommToNetwork.add(communityPropButton);

		final JButton convertOtherCommunityButton = new JButton();
		convertOtherCommunityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralCommunity commNet = new GeneralCommunity();
//					CommunityBuilder.loadCommunityFromOtherFile(commNet,
//							networkFiles[i],"","","");

					commNet.saveCommunityToFile(TextFieldSavePath.getText()
							+ "//" + commNet.getCommunityID() + ".comm", false);

				}

			}
		});
		convertOtherCommunityButton.setText("convert other community");
		panelCommunityAnalysis.add(convertOtherCommunityButton);

		final JPanel panel_20 = new JPanel();
		panel_20.setBorder(new LineBorder(Color.black, 1, false));
		panelCommunityAnalysis.add(panel_20);

		final JLabel communityNetLabel = new JLabel();
		panel_20.add(communityNetLabel);
		communityNetLabel.setText("community net");

		final JButton communityNetToButton = new JButton();
		panel_20.add(communityNetToButton);
		communityNetToButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					CommunityNetwork commNet = new CommunityNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
//					RawData.formatToPajekNet(commNet, TextFieldSavePath
//							.getText()
//							+ "//", "", "");

				}

			}
		});
		communityNetToButton.setText("community net to pajek net");

		final JButton getDegreeButton = new JButton();
		getDegreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					CommunityNetwork commNet = new CommunityNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
//					new NetworkAnalysis().saveDegrees(commNet,
//							TextFieldSavePath.getText() + "//", commNet
//									.getNetworkID().split("\\.")[0],
//							".comNet.degree");
				}

			}
		});
		getDegreeButton.setText("get degree");
		panel_20.add(getDegreeButton);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

			}
		});
		panel_20.add(button_1);
		button_1.setText("get link weight");

		final JButton getNodeNodeweightButton = new JButton();
		getNodeNodeweightButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					CommunityNetwork commNet = new CommunityNetwork();
					NetworkLoader.loadDataFromOneFile(commNet,
							networkFiles[i]);
					float [][] w = commNet.getNNW_NLW();
					
					for(int j=0;j<w.length;j++){
					Debug.outn(w[j][0]+"\t"+w[j][1]);
					}
				
				}
			}
		});
		getNodeNodeweightButton.setText("get node node-weight");
		panel_20.add(getNodeNodeweightButton);

		final JButton getNodeLinkWeightButton = new JButton();
		getNodeLinkWeightButton.setText("get node link-weight");
		panel_20.add(getNodeLinkWeightButton);

		final JButton communityMatrixButton = new JButton();
		communityMatrixButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "",
							".cnm.community");
					NetMatrix.saveCommunityMatrixToFile(generalCommunity,
							TextFieldSavePath.getText() + "//", "", "");

				}
				
			}
		});
		panelCommunityAnalysis.add(communityMatrixButton);
		communityMatrixButton.setText("community matrix");

		final JButton computeQButton = new JButton();
		computeQButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					// first, load networks
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "",
							".comm");
					;
					// Debug.outn(networkFiles[i]) ;
					Debug.outn(generalNet.getNetID()+" "+NetMatrix.matrixModularity(generalNet, generalCommunity));
							//+" "+CommunityTools.NewmanQ(generalCommunity, generalNet));
				}
				
			}
		});
		computeQButton.setText("compute Q");
		panelCommunityAnalysis.add(computeQButton);

		panelCommunityAnalysis.add(getJButtonCommunityEvolve(), null);
		final JPanel scrollPane_1 = new JPanel();
		tabbedPane.addTab("Ling Grahp", null, scrollPane_1, null);

		final JButton visualizeTopologyButton = new JButton();
		visualizeTopologyButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {

					GeneralNetwork gelNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(gelNet,
							networkFiles[i]);
//					LineNetwork LNet = new LineNetwork();
//					LNet.getLineGraph(gelNet, TextFieldSavePath.getText()
//							+ "//", gelNet.getNetworkID().split("\\.")[0],
//							".LGNet");
				}

			}
		});
		visualizeTopologyButton.setText("convert to line graph");

		scrollPane_1.add(visualizeTopologyButton);

		final JScrollPane scrollPaneVisualizeTop = new JScrollPane();
		scrollPane_1.add(scrollPaneVisualizeTop);

		final JPanel panelModel = new JPanel();
		tabbedPane.addTab("Model", null, panelModel, null);

		final JPanel panelReconstruct = new JPanel();
		panelReconstruct.setLayout(new BoxLayout(panelReconstruct, BoxLayout.X_AXIS));
		panelReconstruct.setBorder(new LineBorder(Color.black, 1, false));
		panelModel.add(panelReconstruct);

		final JLabel saveIntervalLabel = new JLabel();
		saveIntervalLabel.setName("saveIntervalLabel");
		panelReconstruct.add(saveIntervalLabel);
		saveIntervalLabel.setText("save interval");

		final JSlider sliderRewireSaveInterval = new JSlider();
		sliderRewireSaveInterval.setName("sliderRewireSaveInterval");
		sliderRewireSaveInterval.setMajorTickSpacing(1);
		sliderRewireSaveInterval.setPaintLabels(true);
		sliderRewireSaveInterval.setPaintTicks(true);
		sliderRewireSaveInterval.setValue(5);
		sliderRewireSaveInterval.setMinorTickSpacing(1);
		sliderRewireSaveInterval.setMaximum(10);
		panelReconstruct.add(sliderRewireSaveInterval);

		final JLabel maxRewirePercentLabel = new JLabel();
		maxRewirePercentLabel.setName("maxRewirePercentLabel");
		maxRewirePercentLabel.setText("max rewire percent");
		panelReconstruct.add(maxRewirePercentLabel);

		final JSlider sliderRewirePercent = new JSlider();
		sliderRewirePercent.setName("sliderRewirePercent");
		sliderRewirePercent.setMaximum(50);
		panelReconstruct.add(sliderRewirePercent);
		sliderRewirePercent.setPaintLabels(true);
		sliderRewirePercent.setMinimum(1);
		sliderRewirePercent.setValueIsAdjusting(true);
		sliderRewirePercent.setValue(5);
		sliderRewirePercent.setPaintTicks(true);
		sliderRewirePercent.setMinorTickSpacing(10);
		sliderRewirePercent.setMajorTickSpacing(4);

		final JButton reconstructButton = new JButton();
		reconstructButton.setName("reconstructButton");
		panelReconstruct.add(reconstructButton);
		reconstructButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					// RawData.formatToPajekNet(commNet,
					// TextFieldSavePath.getText()+"//", "", "");
//					AbstractNetwork geNetCopy = geNet.clone();
//					Reconstruct.randomRewireLinkWithImmutableDegree(geNet,
//							sliderRewireSaveInterval.getValue(),
//							sliderRewirePercent.getValue(), TextFieldSavePath
//									.getText()
//									+ "//",
//							geNet.getNetworkID().split("\\.")[0], "");
				}

			}
		});
		reconstructButton.setText("random Rewire");

		final JPanel panel_19 = new JPanel();
		panel_19.setName("panel_19");
		panelReconstruct.add(panel_19);

		final JCheckBox astCheckBox = new JCheckBox();
		astCheckBox.setSelected(true);
		astCheckBox.setText("ast");
		panel_19.add(astCheckBox);

		final JCheckBox dstCheckBox = new JCheckBox();
		dstCheckBox.setSelected(true);
		dstCheckBox.setText("dst");
		panel_19.add(dstCheckBox);

		final JButton correlationRewireButton = new JButton();
		panel_19.add(correlationRewireButton);
		correlationRewireButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					if (astCheckBox.isSelected()) {
						Reconstruct.monotonicCorrelationRewire(true,true,
								geNet, sliderRewireSaveInterval.getValue(),
								sliderRewirePercent.getValue(),
								TextFieldSavePath.getText() + "//", geNet
										.getNetID().split("\\.")[0], "");
					}
					
					if (dstCheckBox.isSelected()) {
						Reconstruct.monotonicCorrelationRewire(true,false,
								geNet, sliderRewireSaveInterval.getValue(),
								sliderRewirePercent.getValue(),
								TextFieldSavePath.getText() + "//", geNet
										.getNetID().split("\\.")[0], "");
					}
				}

			}
		});
		correlationRewireButton.setText("correlation rewire ");

		final JPanel panel_26 = new JPanel();
		panelModel.add(panel_26);

		final JButton correlationRewireButton_1 = new JButton();
		panel_26.add(correlationRewireButton_1);
		correlationRewireButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,networkFiles[i]);
					if (astCheckBox.isSelected()) {
						Reconstruct.monotonicCorrelationRewire(connectedCheckBox.isSelected(),
										true, geNet, sliderRewireSaveInterval
												.getValue(),
										sliderRewirePercent.getValue(),
										TextFieldSavePath.getText() + "//",
										geNet.getNetID().split("\\.")[0],
										"");
					}
					if (dstCheckBox.isSelected()) {
						Reconstruct
								.monotonicCorrelationRewire(connectedCheckBox.isSelected(),
										false, geNet, sliderRewireSaveInterval
												.getValue(),
										sliderRewirePercent.getValue(),
										TextFieldSavePath.getText() + "//",
										geNet.getNetID().split("\\.")[0],
										"");
					}
				}

			}
		});
		correlationRewireButton_1.setText("correlation rewire");

		
		panel_26.add(connectedCheckBox);

		final JButton superRewireButton = new JButton();
		superRewireButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					
						Reconstruct.reserveDegreeCorrelationRewire(connectedCheckBox.isSelected(),
										 geNet, sliderRewireSaveInterval.getValue(),
										sliderRewirePercent.getValue(),
										TextFieldSavePath.getText() + "//",
										geNet.getNetID(),
										"");
					
				}
				
			}
		});
		superRewireButton.setText("reserveDegreeCorrelationRewire");
		panel_26.add(superRewireButton);

		final JPanel panel_25 = new JPanel();
		panelModel.add(panel_25);

		final JButton repeatCorrelationRewireButton = new JButton();
		panel_25.add(repeatCorrelationRewireButton);
		repeatCorrelationRewireButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
								
					
					//rewire
					String[] networkFiles = getSelectedFiles();
					for (int i = 0; i < networkFiles.length; i++) {
						GeneralNetwork geNet = new GeneralNetwork();
						NetworkLoader.loadDataFromOneFile(geNet,
								networkFiles[i]);
						
						int rewirePercent= Integer.valueOf(sliderRewirePercent.getValue())*100;
						int saveInterval = Integer.valueOf(sliderRewireSaveInterval.getValue());
						int saveFileNum = rewirePercent/saveInterval+1;
								
						double [] sumCorrelation=new double[saveFileNum];
						double [] sumModularity=new double[saveFileNum];
						
						
						int repeatNumber=Integer.valueOf( textFieldRewireRepeatNum.getText() );
						
						for(int repeat=repeatNumber;repeat>0;repeat--){
						
							if (astCheckBox.isSelected()) {
								Reconstruct
										.monotonicCorrelationRewire(true,
												true, geNet, sliderRewireSaveInterval
														.getValue(),
												sliderRewirePercent.getValue(),
												TextFieldSavePath.getText() + "//",
												geNet.getNetID().split("\\.")[0],
												"");
							}
							if (dstCheckBox.isSelected()) {
								Reconstruct
										.monotonicCorrelationRewire(true,
												false, geNet, sliderRewireSaveInterval
														.getValue(),
												sliderRewirePercent.getValue(),
												TextFieldSavePath.getText() + "//",
												geNet.getNetID().split("\\.")[0],
												"");
							}
							
							
							
							/* degree correlation  and modularity for each rewired net */
								//degree correlation
							String astOrDst="";
							if (dstCheckBox.isSelected()) {
								astOrDst="dstDCR";
							}
							if (astCheckBox.isSelected()) {
								astOrDst="astDCR";

							}
							
							for (int j = 0; j <saveFileNum; j++) {
																
								
								String rewireName = geNet.getNetID().split("\\.")[0]
								      +"."+ MathTool.numberString(4,j*saveInterval)
								      +"."+astOrDst;
								Debug.outn("rewireName "+rewireName);
								AbstractNetwork rewireNet=new GeneralNetwork(); 
								NetworkLoader.loadDataFromOneFile(rewireNet,TextFieldSavePath.getText() + 
										"//"
										+rewireName
								);	
								
								sumCorrelation[j]+=	Correlation.degreeCorrelation(rewireNet);
																					
//								sumModularity[j]+=new NetworkAnalysis().network2Community(rewireNet,
//										"", "","").getModularity();
																
							}/////// get correlation and modularity
							
							
							
							
							/*get community*/	
							
						}////////////repeat rewire 
						
						double [] avgCorrelation = new double[saveFileNum];
						double [] avgModularity = new double[saveFileNum];
						for(int k=0;k<saveFileNum;k++){
							avgCorrelation[k]=sumCorrelation[k]/repeatNumber;
							avgModularity[k]=sumModularity[k]/repeatNumber;
							
						}
						Debug.outn(avgCorrelation,"avg cor");
						Debug.outn(avgModularity,"avg modularity");

						
					}////////////////each file
					
					
					
				
				
			}
		});
		repeatCorrelationRewireButton.setText("repeat correlation rewire");

		textFieldRewireRepeatNum = new JTextField();
		panel_25.add(textFieldRewireRepeatNum);
		textFieldRewireRepeatNum.setPreferredSize(new Dimension(50, 20));

		final JPanel panelSpecialTop = new JPanel();
		panelSpecialTop.setBorder(new LineBorder(Color.black, 1, false));
		panelModel.add(panelSpecialTop);

		final JLabel specialTopologyLabel = new JLabel();
		panelSpecialTop.add(specialTopologyLabel);
		specialTopologyLabel.setText("Special topology");

		final JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.GRAY);
		panelSpecialTop.add(panel_13);

		final JButton baModelButton = new JButton();
		panel_13.add(baModelButton);
		baModelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

		

			}
		});
		baModelButton.setText("BA model");

		final JLabel nodeNumLabel = new JLabel();
		nodeNumLabel.setText("node num");
		panel_13.add(nodeNumLabel);

		textFieldBANetSize = new JTextField();
		textFieldBANetSize.setPreferredSize(new Dimension(50, 20));
		panel_13.add(textFieldBANetSize);

		final JLabel degreeLabel = new JLabel();
		degreeLabel.setText("degree");
		panel_13.add(degreeLabel);

		textFieldBADegree = new JTextField();
		textFieldBADegree.setPreferredSize(new Dimension(50, 20));
		panel_13.add(textFieldBADegree);

		final JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.GRAY);
		panelSpecialTop.add(panel_12);

		final JButton erModelButton = new JButton();
		panel_12.add(erModelButton);
		erModelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			
			}
		});
		erModelButton.setText("ER model");

		final JLabel label = new JLabel();
		label.setText("node num");
		panel_12.add(label);

		textFieldERNetSize = new JTextField();
		textFieldERNetSize.setPreferredSize(new Dimension(40, 20));
		panel_12.add(textFieldERNetSize);

		final JLabel pLabel = new JLabel();
		pLabel.setText("p");
		panel_12.add(pLabel);

		textFieldERP = new JTextField();
		textFieldERP.setPreferredSize(new Dimension(50, 20));
		panel_12.add(textFieldERP);

		final JPanel panel_31 = new JPanel();
		panelSpecialTop.add(panel_31);

		final JButton communityButton = new JButton();
		communityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				int commSize=  Integer.valueOf(textFieldCommModelInitCommSize.getText());
				int subCommSize=  Integer.valueOf(textFieldCommModelInitSubcommSize.getText());

//				CommunityModel communityModel = new CommunityModel(2000, 10,5,3,
//						0.9 , 0.02 , -1 );				
			//	UndirectedSparseGraph modelNet = communityModel.getTopNet("communityModel.adj",400);
				
			
			
			}
		});
		communityButton.setText("community");
		panel_31.add(communityButton);

		final JLabel commSizeLabel = new JLabel();
		commSizeLabel.setText("comm size");
		panel_31.add(commSizeLabel);

		textFieldCommModelInitCommSize = new JTextField();
		textFieldCommModelInitCommSize.setText("5");
		textFieldCommModelInitCommSize.setPreferredSize(new Dimension(40, 20));
		panel_31.add(textFieldCommModelInitCommSize);

		final JLabel nodeNumLabel_1 = new JLabel();
		nodeNumLabel_1.setText("node num");
		panel_31.add(nodeNumLabel_1);

		textFieldCommModelInitSubcommSize = new JTextField();
		textFieldCommModelInitSubcommSize.setText("10");
		textFieldCommModelInitSubcommSize.setPreferredSize(new Dimension(40, 20));
		panel_31.add(textFieldCommModelInitSubcommSize);

		final JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.GRAY);
		panelSpecialTop.add(panel_11);

		final JButton latticeButton = new JButton();
		panel_11.add(latticeButton);
		latticeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

			
			}
		});
		latticeButton.setText("Lattice model");

		textFieldRowCount = new JTextField();
		panel_11.add(textFieldRowCount);
		textFieldRowCount.setPreferredSize(new Dimension(50, 20));

		textFieldColCount = new JTextField();
		panel_11.add(textFieldColCount);
		textFieldColCount.setPreferredSize(new Dimension(50, 20));

		final JButton completebipartiteButton = new JButton();
		completebipartiteButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				;
//				int setA = Integer.parseInt(textFieldCBSetA.getText());
//				int setB = Integer.parseInt(textFieldCBSetB.getText());
//
//				SpecialTopology.saveModel2File(SpecialTopology
//						.completeBipartite(setA, setB), TextFieldSavePath
//						.getText()
//						+ "//", "completeBipartite" + setA + "-" + setB,
//						".model");

			}
		});
		completebipartiteButton.setText("Complete Bipartite");
		panelModel.add(completebipartiteButton);

		textFieldCBSetA = new JTextField();
		textFieldCBSetA.setPreferredSize(new Dimension(40, 20));
		panelModel.add(textFieldCBSetA);

		textFieldCBSetB = new JTextField();
		textFieldCBSetB.setPreferredSize(new Dimension(40, 20));
		panelModel.add(textFieldCBSetB);

		final JButton weakenCommunityRewireButton = new JButton();
		panelModel.add(weakenCommunityRewireButton);
		weakenCommunityRewireButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					
					GeneralCommunity generalCommunity = new GeneralCommunity();
					// Debug.outn("net path "+(new
					// File(networkFiles[i])).getPath());

					// load community according to networks loaded
					CommunityBuilder.loadCommunityFromFile(generalCommunity,
							new File(networkFiles[i]).getPath(), "",
							".community");

//					Reconstruct.weakenCommunity(geNet, generalCommunity, sliderRewireSaveInterval.getValue(),
//							sliderRewireSaveInterval.getValue(), TextFieldSavePath
//							.getText()
//							+ "//", geNet.getNetworkID().split("\\.")[0], "");
					
				}

			}
		});
		weakenCommunityRewireButton.setText("weaken community rewire");

		final JButton maxCorrelationNetButton = new JButton();
		panelModel.add(maxCorrelationNetButton);
		maxCorrelationNetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					Reconstruct.constructMaxCorrelationNetwork2(geNet,
							TextFieldSavePath.getText() + "//", geNet
									.getNetID().split("\\.")[0], ".maxCor");
				}

			}
		});
		maxCorrelationNetButton.setText("max correlation net");

		final JPanel panel_23 = new JPanel();
		tabbedPane.addTab("Spread", null, panel_23, null);

		final JLabel epidemicRateLabel = new JLabel();
		epidemicRateLabel.setText("epidemic rate");
		panel_23.add(epidemicRateLabel);

		final JPanel panel_24 = new JPanel();
		panel_24.setLayout(new CardLayout());
		panel_23.add(panel_24);

		textFieldEpidemicRate = new JTextField();
		textFieldEpidemicRate.setText("0.003");
		textFieldEpidemicRate.setPreferredSize(new Dimension(50, 20));
		panel_23.add(textFieldEpidemicRate);

		final JLabel recoveryRateLabel = new JLabel();
		recoveryRateLabel.setText("recovery rate");
		panel_23.add(recoveryRateLabel);

		textFieldRecoveryRate = new JTextField();
		textFieldRecoveryRate.setText("0.001");
		textFieldRecoveryRate.setPreferredSize(new Dimension(50, 20));
		panel_23.add(textFieldRecoveryRate);

		final JLabel initialInfectedRateLabel = new JLabel();
		initialInfectedRateLabel.setText("initial infected rate");
		panel_23.add(initialInfectedRateLabel);

		textFieldInitialInfectiousRate = new JTextField();
		textFieldInitialInfectiousRate.setText("0.05");
		textFieldInitialInfectiousRate.setPreferredSize(new Dimension(50, 20));
		panel_23.add(textFieldInitialInfectiousRate);

		final JPanel panel_30 = new JPanel();
		panel_30.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel_23.add(panel_30);

		final JLabel label_3 = new JLabel();
		label_3.setText("start tao");
		panel_30.add(label_3);

		textFieldStartTao = new JTextField();
		textFieldStartTao.setText("0");
		textFieldStartTao.setPreferredSize(new Dimension(50, 20));
		panel_30.add(textFieldStartTao);

		final JLabel label_4 = new JLabel();
		label_4.setText("interval");
		panel_30.add(label_4);

		textFieldTaoInterval = new JTextField();
		textFieldTaoInterval.setText("0.02");
		textFieldTaoInterval.setPreferredSize(new Dimension(50, 20));
		panel_30.add(textFieldTaoInterval);

		final JLabel label_5 = new JLabel();
		label_5.setText("end tao");
		panel_30.add(label_5);

		textFieldEndTao = new JTextField();
		textFieldEndTao.setText("2");
		textFieldEndTao.setPreferredSize(new Dimension(50, 20));
		panel_30.add(textFieldEndTao);

		final JLabel label_6 = new JLabel();
		label_6.setText("fixed");
		panel_30.add(label_6);

		final JRadioButton radioButtonFixS = new JRadioButton();
		radioButtonFixS.setSelected(true);
		radioButtonFixS.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			}
		});
		radioButtonFixS.setText("S");
		panel_30.add(radioButtonFixS);

		final JRadioButton radioButtonFixR = new JRadioButton();
		radioButtonFixR.setText("R");
		panel_30.add(radioButtonFixR);
		
		ButtonGroup fixGroup = new ButtonGroup();
		fixGroup.add(radioButtonFixS);
		fixGroup.add(radioButtonFixR);
		textFieldFixedS = new JTextField();
		textFieldFixedS.setText("0.002");
		textFieldFixedS.setPreferredSize(new Dimension(50, 20));
		panel_30.add(textFieldFixedS);

		final JButton spreadButton_1 = new JButton();
		spreadButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				float startTao =Float.valueOf(textFieldStartTao.getText());
				float endTao =Float.valueOf(textFieldEndTao.getText());
				float intervalTao =Float.valueOf(textFieldTaoInterval.getText());
				
				double initialInfectedRate =Double.valueOf(textFieldInitialInfectiousRate.getText());
				
				int repeatNum=Integer.valueOf( textFieldSpreadRepeat.getText() );
				//double fixS = 
				double fixS=0;
				double fixR=0;
				if( radioButtonFixS.isSelected()){
					fixS = Double.valueOf(textFieldFixedS.getText());
				}
				if( radioButtonFixR.isSelected()){
					fixR = Double.valueOf(textFieldFixedS.getText());
				}
								
				String[] networkFiles = getSelectedFiles();
				//each network
				for (int i = 0; i < networkFiles.length; i++) {
					
					/*spread from start Tao - end Tao*/
					double spreadRate=fixS;
					double recoveryRate=fixR;
					//Debug.outn(i);								
					for(float T = startTao;T<=endTao;T+=intervalTao){
						
						if(T==0) continue;
						
						T= (float)Math.round(T*1000)/1000 ;
						if(fixS==0){
							spreadRate = recoveryRate*T;
						}
						if(fixR==0){
							recoveryRate=spreadRate/T;
						}
						
						
						Debug.outn(T+" "+" S "+spreadRate+" R "+recoveryRate);
						
//						NetworkImmuine  gelNet = new NetworkImmuine (spreadRate,initialInfectedRate,recoveryRate);
//						NetworkLoader.loadDataFromOneFile(gelNet,
//								networkFiles[i]);
//
//						float immRate=0;
//					
//						double avgEachStepInfectedRate[] = gelNet.spreadAction(immRate,saveSpreadNetCheckBox.isSelected(),repeatNum , networkFiles[i],"","");
//						StringBuffer avgIRateStr = new StringBuffer();
//						
//						for(int d=0;d<avgEachStepInfectedRate.length;d++){
//							avgIRateStr.append(Double.toString(avgEachStepInfectedRate[d])+"\n");
//						}						
//						
//						String taoInt = Float.toString(T).split("\\.")[0];
//						String taoDecimal  = Float.toString(T).split("\\.")[1];
//						
//						/* avg stable final I rate*/
//						float sumTao = 0;
//						int avgCount=100;
//						for(int m=0;m<avgCount;m++ ){
//							sumTao+=avgEachStepInfectedRate[avgEachStepInfectedRate.length-1-m];
//						}
//						
//						
//						//Debug.outn("taoInt "+taoInt+"  "+taoDecimal);
//					    
//						FileOperation.saveStringToFile(networkFiles[i]+taoInt+"_"+taoDecimal+".spread", avgIRateStr.toString(), false);
//						
//						float dCor =Correlation.degreeCorrelation(gelNet);
//						StringBuffer eachStableTaoInfectedP = new StringBuffer();
//						eachStableTaoInfectedP.append(dCor+" "+T+" "+sumTao/avgCount +"\n");
//						FileOperation.saveStringToFile(TextFieldSavePath.getText() + "//"+"spread.EachTaoSpread", 
//								eachStableTaoInfectedP.toString(), true);
						
						
					}////each tao	
					
					
					
				}//for each file				
				
			}
		});
		panel_30.add(spreadButton_1);
		spreadButton_1.setText("spread");

		final JLabel label_2 = new JLabel();
		panel_23.add(label_2);
		label_2.setText("repeat");

		textFieldSpreadRepeat = new JTextField();
		textFieldSpreadRepeat.setText("1");
		textFieldSpreadRepeat.setPreferredSize(new Dimension(50, 20));
		panel_23.add(textFieldSpreadRepeat);

		final JPanel panel_27 = new JPanel();
		panel_23.add(panel_27);

		
		panel_27.add(saveSpreadNetCheckBox);

		final JLabel label_1 = new JLabel();
		label_1.setText("interval");
		panel_27.add(label_1);

		textFieldSpreadSaveInterval = new JTextField();
		textFieldSpreadSaveInterval.setText("5");
		textFieldSpreadSaveInterval.setPreferredSize(new Dimension(50, 20));
		panel_27.add(textFieldSpreadSaveInterval);

		final JPanel panel_28 = new JPanel();
		panel_23.add(panel_28);

		final JCheckBox immunizeCheckBox = new JCheckBox();
		immunizeCheckBox.setText("immunize");
		panel_28.add(immunizeCheckBox);

		final JRadioButton targetRadioButton = new JRadioButton();
		targetRadioButton.setText("target");
		panel_28.add(targetRadioButton);

		final JButton spreadButton = new JButton();
		panel_23.add(spreadButton);
		spreadButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				double spreatRate =Double.valueOf(textFieldEpidemicRate.getText());
				double initialRate =Double.valueOf(textFieldInitialInfectiousRate.getText());
				double recoveryRate =Double.valueOf(textFieldRecoveryRate.getText());
				int repeatNum=Integer.valueOf( textFieldSpreadRepeat.getText() );

				String[] networkFiles = getSelectedFiles();
				//each network
				for (int i = 0; i < networkFiles.length; i++) {

//					NetworkImmuine  gelNet = new NetworkImmuine (spreatRate,initialRate,recoveryRate);
//					NetworkLoader.loadDataFromOneFile(gelNet,
//							networkFiles[i]);
//					//gelNet.initNodesStat(gelNet.NodeState_SUSCEPTIBLE);
//					//gelNet.randomChangeNodesStat(gelNet.getNodeIdArray(), initialRate, IImmuineNetwork.nodeState_INFECTIOUS);
////					Clock clock = Clock.instance();
////					clock.addClockListener(gelNet);
////					clock.startTicking(500);
//					float immRate=0;
//					if(immunizeCheckBox.isSelected()){
//						immRate=0.02f;
//					}
//					double rate[] = gelNet.spreadAction(immRate,saveSpreadNetCheckBox.isSelected(),repeatNum , networkFiles[i],"","");
//					StringBuffer sb = new StringBuffer();
//					
//					for(int d=0;d<rate.length;d++){
//						sb.append(Double.toString(rate[d])+"\n");
//					}
//					String tao = Double.toString( spreatRate/recoveryRate );
//					
//					
//					String taoInt = tao.split("\\.")[0];
//					String taoDecimal  = tao.split("\\.")[1];
//					//Debug.outn("tao "+taoInt+" "+taoDecimal);
//				    
//					FileOperation.saveStringToFile(networkFiles[i]+taoInt+"_"+taoDecimal+".spread", sb.toString(), false);
//					
				}
				
			}
		});
		
		spreadButton.setText(" spread");

		final JButton spreadSpeedButton = new JButton();
		spreadSpeedButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				//each network
				for (int i = 0; i < networkFiles.length; i++) {
					
					GeneralNetwork generalNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(generalNet,
							networkFiles[i]);
					//load spread file
					
				}
				
			}
		});
		spreadSpeedButton.setText("spread speed");
		panel_23.add(spreadSpeedButton);

		final JPanel panel_29 = new JPanel();
		tabbedPane.addTab("Path", null, panel_29, null);

		final JPanel panelShortestPath = new JPanel();
		panel_29.add(panelShortestPath);

		final JButton hopCountButton = new JButton();
		hopCountButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					//Debug.outn(geNet.getNetworkID());
					String fileName = geNet.getNetID()
							 +
							// geNet.getNetworkID().split("\\.")[2]+
							".hopCount";
					int [] hopCount = ShortestPath.getHopCount(geNet);
					
					Debug.outn(geNet.getNetID()+" "+MathTool.average(hopCount)+" "
							+MathTool.stdDeviation(hopCount));
					
					
//					StringBuffer hopCountStr = new StringBuffer();
//					for(int p=0;p<hopCount.length;p++){
//						
//						hopCountStr.append(hopCount[p]+"\n");
//					}
//					
//					FileOperation.saveStringToFile(TextFieldSavePath.getText()
//							+ "//" + fileName, 						
//							hopCountStr.toString(), false);
					// geNet.getNetworkID().split("\\.")[1]+" "+
	
				}
				
			}
		});
		hopCountButton.setText("Hop count");
		panelShortestPath.add(hopCountButton);

		final JButton avgButton = new JButton();
		avgButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					//Debug.outn(geNet.getNetworkID());
					String fileName = geNet.getNetID()
							 +
							// geNet.getNetworkID().split("\\.")[2]+
							".hopCount";
					String netID = new File(networkFiles[i]).getName();
					
					float [] hopcount = ShortestPath.getAvgAndReciprocalHopCount(geNet);
												
					Debug.outn(geNet.getNetID()+" "+hopcount[0]);
					
					String output=geNet.getNetID()+"\t"+Float.toString(hopcount[0])+
							"\t"+Float.toString(hopcount[1])+"\n";
					
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0]+"."+netID.split("\\.")[1] + ".avgRplHopcount", 
							output,
							true);
				

				}
				
			}
		});
		avgButton.setText("avg hop count");
		panel_29.add(avgButton);

		final JButton reciprocalHopcountButton = new JButton();
		reciprocalHopcountButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					GeneralNetwork geNet = new GeneralNetwork();
					NetworkLoader.loadDataFromOneFile(geNet,
							networkFiles[i]);
					//Debug.outn(geNet.getNetworkID());
					String fileName = geNet.getNetID()
							 +
							// geNet.getNetworkID().split("\\.")[2]+
							".hopCount";
					String netID = new File(networkFiles[i]).getName();
					
					float reciprocalHopcount =0;
					
						reciprocalHopcount = ShortestPath.reciprocalHopcount(geNet);
													
					Debug.outn(geNet.getNetID()+" "+reciprocalHopcount);
					
					FileOperation.saveStringToFile(TextFieldSavePath.getText()
							+ "//" + netID.split("\\.")[0]+"."+netID.split("\\.")[1] + ".reciprocalHopcount", 
							String.valueOf(reciprocalHopcount),
							true);					

				}
				
			}
		});
		reciprocalHopcountButton.setText(" reciprocal hopcount");
		panel_29.add(reciprocalHopcountButton);

		final JPanel panel_tool = new JPanel();
		tabbedPane.addTab("Tool", null, panel_tool, null);

		final JPanel panelToolFile = new JPanel();
		panelToolFile.setBorder(new LineBorder(Color.black, 1, false));
		panel_tool.add(panelToolFile);

		panel_tool.add(getJButtonIPV6Data(), null);
		panel_tool.add(getJButtonDelNodeWithDegree(), null);
		panel_tool.add(getJButtonConvertMRWCommunityData(), null);
		final JLabel fileOperationLabel = new JLabel();
		fileOperationLabel.setText("file operation");
		panelToolFile.add(fileOperationLabel);

		final JButton changeNameButton = new JButton();
		changeNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String[] networkFiles = getSelectedFiles();
				for (int i = 0; i < networkFiles.length; i++) {
					File network = new File(networkFiles[i]);
					String netID = (network.getName().split("\\."))[0];
					// Debug.outn(netID);
					FileOperation.ChangeFileName(network, netID + ".comNet");
				}

			}
		});
		changeNameButton.setText("Change name");
		panelToolFile.add(changeNameButton);
		
		JPanel panel_testNet = new JPanel();
		panel_tool.add(panel_testNet);
		
		JButton btnNewDumpNet = new JButton("dump");
		btnNewDumpNet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
											
					for(String netFile:getSelectedFiles()){
						GeneralNetwork gelNet = new GeneralNetwork();
						gelNet.loadNetworkData(	netFile);
						TestNetwork.dump(gelNet);
					}							
			}
		});
		panel_testNet.add(btnNewDumpNet);
		
		JPanel panel_link_prediciton = new JPanel();
		tabbedPane.addTab("link prediction", null, panel_link_prediciton, null);
		panel_link_prediciton.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.3);
		panel_link_prediciton.add(splitPane_2);
		
		JPanel panel_32 = new JPanel();
		splitPane_2.setLeftComponent(panel_32);
		
		JButton btnNewButton = new JButton("test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				for(String netFile:getSelectedFiles()){
					GeneralNetwork gelNet = new GeneralNetwork();
					gelNet.loadNetworkData(	netFile);
					LinkPrediction lp=new LinkPrediction();
					lp.test(gelNet);
					
				}							
				
			}
		});
		panel_32.add(btnNewButton);
		
		JPanel panel_33 = new JPanel();
		splitPane_2.setRightComponent(panel_33);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout());
		splitPane_1.setLeftComponent(panel_2);

		final JButton setSavePathButton = new JButton();
		panel_2.add(setSavePathButton);
		setSavePathButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setSavePath();
			}
		});
		setSavePathButton.setText("set save path");

		TextFieldSavePath = new JTextField();
		TextFieldSavePath.setPreferredSize(new Dimension(350, 20));
		panel_2.add(TextFieldSavePath);

		final JCheckBox specifyNameCheckBox = new JCheckBox();
		specifyNameCheckBox.setText("specify name");
		panel_2.add(specifyNameCheckBox);

		JButton buttonLoadNet;
		// panel_9.add(deleteButton);

		final JPanel panel_4 = new JPanel();
		panel_4.setLayout(new BorderLayout());
		panel.add(panel_4);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, 400));
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panel_4.add(scrollPane);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(new Color(192, 192, 192));
		scrollPane.setAutoscrolls(true);
		scrollPane.setName("scrollPane");

		loadFilelist = new JList();
		scrollPane.setColumnHeaderView(loadFilelist);
		scrollPane.setViewportView(loadFilelist);
		loadFilelist.setVisibleRowCount(10);
		loadFilelist
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		loadFilelist.setModel(new DefaultListModel());

		final JPanel panel_9 = new JPanel();
		panel_9.setLayout(new FlowLayout());
		panel.add(panel_9);

		textFieldLastFilePath = new JTextField();
		textFieldLastFilePath.setPreferredSize(new Dimension(200, 20));
		panel_9.add(textFieldLastFilePath);

		final JLabel filesLoadLabel = new JLabel();
		filesLoadLabel.setText("files");
		panel_9.add(filesLoadLabel);
		buttonLoadNet = new JButton();
		panel_9.add(buttonLoadNet);
		buttonLoadNet.setName("button");
		buttonLoadNet.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				
				addFileToList(selectFiles((String) textFieldLoadFilter
						.getSelectedItem()));
				filesLoadLabel.setText(((DefaultListModel) loadFilelist
						.getModel()).getSize()
						+ " files");

			}
		});

		buttonLoadNet.setText("load data");

		copyLoadDirectoryCheckBox = new JCheckBox();
		copyLoadDirectoryCheckBox.setSelected(true);

		copyLoadDirectoryCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			}
		});
		panel_9.add(copyLoadDirectoryCheckBox);
		copyLoadDirectoryCheckBox.setText("copy directory to save");

		final JLabel CheckBoxFileFilter = new JLabel();
		panel_9.add(CheckBoxFileFilter);
		CheckBoxFileFilter.setText("file filter");

		textFieldLoadFilter = new JComboBox();
		textFieldLoadFilter.setEditable(true);
		textFieldLoadFilter.addItem("");
		textFieldLoadFilter.addItem("largestPartitionNet");
		textFieldLoadFilter.addItem("rewiredNet");
		textFieldLoadFilter.addItem("community");
		textFieldLoadFilter.addItem("comNet");
		textFieldLoadFilter.addItem("model");

		panel_9.add(textFieldLoadFilter);
		textFieldLoadFilter.setPreferredSize(new Dimension(150, 20));

		final JButton buttonClearFileList = new JButton();
		panel_9.add(buttonClearFileList);
		buttonClearFileList.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				deleteFileList();
				filesLoadLabel.setText(((DefaultListModel) loadFilelist
						.getModel()).getSize()
						+ " files");

			}
		});
		buttonClearFileList.setText("delete");

		final JButton clearButton = new JButton();
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				((DefaultListModel) loadFilelist.getModel()).clear();
				filesLoadLabel.setText(((DefaultListModel) loadFilelist
						.getModel()).getSize()
						+ " files");

			}
		});
		clearButton.setText("clear");
		panel_9.add(clearButton);
		panel_9.add(getJTextFieldPostfix(), null);
	}

	public void addFileToList(File[] files) {
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			((DefaultListModel) this.loadFilelist.getModel())
					.addElement(files[i].toString());
		}
		((DefaultListModel) this.loadFilelist.getModel()).getSize();
	}

	public File[] selectFiles(String keyword) {
		// 建立文件选择框对象
		JFileChooser fc = new JFileChooser();
		
		// 设定文件选择框标题
		fc.setDialogTitle("Open File");
		
		// filter file
		if (keyword.trim() != "") {
			fc.setFileFilter(new LoadedFileFilter(keyword));
		}

		fc.setMultiSelectionEnabled(true);

		// Debug.outn(textFieldLastFilePath.getText()+"---");
		if (textFieldLastFilePath.getText().trim().equals("")) {
			fc.setCurrentDirectory(new File("e://complex//"));
		} else {
			Debug.outn("null last path");
			fc.setCurrentDirectory(new File(textFieldLastFilePath.getText()));
		}

		// 显示文件选择框，在选择后将结果储存到returnVal变量中
		int returnVal = fc.showOpenDialog(this.frame);
		// 如果用户选择了文件，并点击了"Opne/打开"按钮，显示用户选择的文件全名路径，
		// 如果用户点击"Close/关闭"按钮，以及其它方式退出文件选择框，则什么也不做。
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			// set save path, same as load path
			if (copyLoadDirectoryCheckBox.isSelected()) {
				TextFieldSavePath.setText(fc.getSelectedFiles()[0].getParent()
						.toString());
			}
			// record the last path
			textFieldLastFilePath.setText(fc.getSelectedFiles()[0].getParent()
					.toString());
			return fc.getSelectedFiles();
		} else {
			return null;
		}

	}

	public File chooseSavePath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("set save path");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File("e://netdata//"));
		int returnVal = fc.showOpenDialog(this.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		} else {
			return null;
		}
	}

	public void setSavePath() {
		File savePath = this.chooseSavePath();
		if (savePath == null) {
			return;
		}

		this.TextFieldSavePath.setText(savePath.toString());
	}

	public void deleteFileList() {
		Object[] selected = this.loadFilelist.getSelectedValues();

		for (int j = 0; j < selected.length; j++) {
			((DefaultListModel) this.loadFilelist.getModel())
					.removeElement(selected[j]);
		}

	}

	public String[] getSelectedFiles() {
		Object[] element = ((DefaultListModel) this.loadFilelist.getModel())
				.toArray();
		if (element.length == 0) {
			return null;
		}
		String[] files = new String[element.length];
		for (int i = 0; i < files.length; i++) {
			files[i] = (String) element[i];
		}
		return files;

	}

}// ////////////////////////////////////////////////////////////////////////MainWin

/**
 * filter the loaded file
 * 
 * @author gexin
 * 
 */
class LoadedFileFilter extends FileFilter {

	String keyword;
	private final java.util.List<String> fileNameExtensionList = new ArrayList<String>();

	public LoadedFileFilter(String postfix) {
		keyword = postfix;
		fileNameExtensionList.add(postfix);

	}

	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		final String fileName = f.getName();
		// int lastIndexOfDot = fileName.lastIndexOf('.');

		// if (lastIndexOfDot == -1)
		// return false;

		// int fileNameLength = fileName.length();
		// final String extension = fileName.substring(lastIndexOfDot + 1,
		// fileNameLength);
		//
		// return fileNameExtensionList.contains(extension);
		// try {
		// Pattern pattern = Pattern.compile(keyword);
		// Matcher match = pattern.matcher(fileName);
		// Debug.outn("fileName "+fileName+"key workd "+keyword+"
		// "+match.matches());
		//			  return match.matches();    
		//			} catch (Exception e) {    
		//				Debug.outn(e);
		//			  return true;    
		//			}    
		int keywordIdx = fileName.indexOf(keyword);
		return (fileName.length() == keywordIdx + keyword.length() && keywordIdx >= 0);
	}

	public String getDescription() {
		StringBuffer buf = new StringBuffer("user defined file");

		return buf.toString();
	}
}// //////////////////////////////////////////file filter

