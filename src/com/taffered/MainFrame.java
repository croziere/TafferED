package com.taffered;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.taffered.analyzer.Decompiler;
import com.taffered.analyzer.Decompiler.DecompilingException;
import com.taffered.analyzer.Decompiler.NoMapSpecifiedException;
import com.taffered.analyzer.darkDB.InvalidDarkDBFileException;
import net.miginfocom.swing.MigLayout;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.vecmath.Point3d;
import java.awt.*;
import java.io.File;


public class MainFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static MainFrame INSTANCE = new MainFrame("Dark / Source Engine converter");

    private JComboBox<String> comboBoxChunkType;

    private Decompiler analyzer;

    private JLabel lbl_miss_file_status;

    private JTree map_tree;

    private JComboBox<String> comboBoxChunkList;

    private JButton btnShow;

    private JPanel panel_1;

    private Canvas3D renderer3D;

    private MainFrame(String title) {
        super(title);

        System.out.println("Loading main frame");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmLoadmiss = new JMenuItem("Load .MISS");
        mntmLoadmiss.addActionListener(e -> {
            JFileChooser fileWindow = new JFileChooser();
            fileWindow.setCurrentDirectory(new File("E:\\projet_thief_source\\Sources\\thief_gold\\GOODIES\\DROMED"));
            fileWindow.showOpenDialog(null);
            if (fileWindow.getSelectedFile() != null) {
                comboBoxChunkList.removeAllItems();
                System.out.println("Map : " + fileWindow.getSelectedFile());
                lbl_miss_file_status.setText("Map : " + fileWindow.getSelectedFile());
                analyzer = new Decompiler(fileWindow.getSelectedFile());

                try {
                    analyzer.decompile();

                    map_tree.setModel(analyzer.getMissFile().getTreeModel());

                    for (String s : analyzer.getMissFile().getChunkNameSet()) {
                        comboBoxChunkList.addItem(s);
                    }

                    map_tree.setVisible(true);

                    renderer3D.setSize(this.getSize());

                    SimpleUniverse univ = new SimpleUniverse(renderer3D);
                    univ.getViewer().getView().setBackClipDistance(100000);
                    univ.getViewingPlatform().setNominalViewingTransform();
                    univ.addBranchGraph(initializeCamera(univ.getViewingPlatform().getViewPlatformTransform(), analyzer.getMissFile().render()));

                } catch (NoMapSpecifiedException | DecompilingException | InvalidDarkDBFileException e1) {
                    // TODO Auto-generated catch block

                    e1.printStackTrace();
                }
            }
        });

        mnFile.add(mntmLoadmiss);

        JTabbedPane analyzer_pan = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(analyzer_pan, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        analyzer_pan.addTab("Analyzer", null, panel, null);
        panel.setLayout(new MigLayout("", "[grow]", "[][][grow]"));

        lbl_miss_file_status = new JLabel("No .miss file selected");
        panel.add(lbl_miss_file_status, "cell 0 0");

        JScrollPane scrollPane = new JScrollPane();
        panel.add(scrollPane, "cell 0 2,grow");

        map_tree = new JTree();
        scrollPane.setViewportView(map_tree);

        JButton btnShowAll = new JButton("Show Raw");
        panel.add(btnShowAll, "cell 0 1");
        btnShowAll.addActionListener(e -> map_tree.setModel(analyzer.getMissFile().getTreeModel()));


        JButton btnShowBrlist = new JButton("Show BRLIST");
        btnShowBrlist.addActionListener(e -> map_tree.setModel(new DefaultTreeModel(analyzer.getMissFile().getBrlist().getTreeNode())));
        panel.add(btnShowBrlist, "cell 0 1");

        JSeparator separator = new JSeparator();
        panel.add(separator, "cell 0 1");

        comboBoxChunkList = new JComboBox<>();
        panel.add(comboBoxChunkList, "cell 0 1");

        btnShow = new JButton("Goto");
        btnShow.addActionListener(arg0 -> map_tree.setModel(new DefaultTreeModel(analyzer.getMissFile().getChunk(comboBoxChunkList.getSelectedItem().toString()).getTreeNode())));
        panel.add(btnShow, "cell 0 1");

        JSeparator separator2 = new JSeparator();
        panel.add(separator2, "cell 0 1");

        comboBoxChunkType = new JComboBox<>();
        comboBoxChunkType.addItem("Master");
        comboBoxChunkType.addItem("Links");
        comboBoxChunkType.addItem("Properties");
        comboBoxChunkType.addItem("LD");
        comboBoxChunkType.addItem("AI");
        comboBoxChunkType.addActionListener(arg0 -> {
            String value = comboBoxChunkType.getSelectedItem().toString();
            switch (value) {
                case "Master":
                    map_tree.setModel(analyzer.getMissFile().getMasterChunksTreeModel());
                    break;
                case "Links":
                    map_tree.setModel(analyzer.getMissFile().getLinksChunksTreeModel());
                    break;
                case "Properties":
                    map_tree.setModel(analyzer.getMissFile().getPropertiesChunksTreeModel());
                    break;
                case "LD":
                    map_tree.setModel(analyzer.getMissFile().getLdChunksTreeModel());
                    break;
                case "AI":
                    map_tree.setModel(analyzer.getMissFile().getAiChunksTreeModel());
                    break;
            }

        });
        panel.add(comboBoxChunkType, "cell 0 1");

        panel_1 = new JPanel();
        analyzer_pan.addTab("Renderer", null, panel_1, null);

        renderer3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        renderer3D.setSize(1080, 720);
        panel_1.add(renderer3D);


        JPanel panel_2 = new JPanel();
        analyzer_pan.addTab("Converter", null, panel_2, null);


        map_tree.setVisible(false);


        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.setVisible(true);
    }

    private BranchGroup initializeCamera(TransformGroup tg, BranchGroup scene) {

        // Creation comportement navigation (rotation) a la souris
       MouseRotate mouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
        mouseRotate.setFactor(0.005);
        mouseRotate.setTransformGroup(tg);

        // Champ d'action de la souris (rotation)
        mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000));

        // Ajout du comportement rotation a la souris a l'objet parent de la
        // scene 3D
        scene.addChild(mouseRotate);

        // Creation comportement navigation (translation) a la souris
       /* MouseTranslate mouseTranslate =
                new MouseTranslate(MouseBehavior.INVERT_INPUT);
        mouseTranslate.setFactor(0.005);
        mouseTranslate.setTransformGroup(tg);*/

        // Champ d'action de la souris (translation)
        /*mouseTranslate.setSchedulingBounds(
                new BoundingSphere(new Point3d(), 1000));*/

        // Ajout du comportement translation a la souris a l'objet parent de la
        // scene 3D
       /* scene.addChild(mouseTranslate);*/

        // Creation comportement navigation (zoom) a la souris
       /* MouseZoom mouseZoom = new MouseZoom(MouseBehavior.INVERT_INPUT);
        mouseZoom.setFactor(0.005);
        mouseZoom.setTransformGroup(tg);*/

        // Champ d'action de la souris (zoom)
       /* mouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));*/

        // Ajout du comportement zoom a la souris a l'objet parent de la
        // scene 3D
       /* scene.addChild(mouseZoom);*/

        KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(tg);
        keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000));

        scene.addChild(keyNavigatorBehavior);

        scene.compile();

        return scene;
    }

    public static MainFrame initialize() {

        return INSTANCE;
    }

}
