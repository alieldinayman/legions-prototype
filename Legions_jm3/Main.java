 package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.Timer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import java.nio.channels.Channel;
 import de.lessvoid.nifty.builder.ControlDefinitionBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import java.util.Collection;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication implements AnimEventListener{
    
    protected float time=0;
    
    protected int i = 0;
    protected int j = 0;
    protected int k = 0;
    protected float teamPos;
    protected float team1Pos = -100f;
    protected float team2Pos = 100f;
    protected float teamDir;
    protected float team1Dir = -0.02f;
    protected float team2Dir = 0.02f;
    static protected float team1BuildLine = -122f;
    static protected float team2BuildLine = 122f;
    static protected int goldR = 200;
    static protected int goldB = 200;
    static protected int castle1HP = 400;
    static protected int castle2HP = 400;
    protected Vector3f dist;
    protected Geometry cube1;
    protected Geometry cube2;
    protected Geometry cube3;
    protected Geometry WayPointr1;
    protected Geometry WayPointr2;
    protected Geometry WayPointb1;
    protected Geometry WayPointb2;

    protected Node castle1;
    protected Node castle2;
    protected Node building1;
    protected Node building2;
    protected Node gladiator1;
    protected Node gladiator2;
    protected Node ogre;
    protected Node[] cubes = new Node[200];
    protected boolean isAlive[] = new boolean[200];
    protected boolean fighting[] = new boolean[200];
    protected boolean fightingCastle1[] = new boolean[200];
    protected boolean fightingCastle2[] = new boolean[200];
    protected boolean castleRUA = false;
    protected boolean castleBUA = false;
    protected boolean tempFlagR = false;
    protected boolean tempFlagB = false;
    protected int[] team = new int[100];
    static public Spatial[] buildingsR = new Spatial[100];
    static public Spatial[] buildingsB = new Spatial[100];
    static boolean[] buildingExistR = new boolean[100];
    static boolean[] buildingExistB = new boolean[100];
    protected float[] timers = new float[100];
    protected int[] timerNewR = new int[100];
    protected int[] timerNewB = new int[100];
    protected int[] HP = new int[200];
    protected boolean SpawnR[] = new boolean[100];
    protected boolean SpawnB[] = new boolean[100];
    protected Geometry TBD;
    
    protected AnimChannel[] gladiatorChannel = new AnimChannel[100];
    protected AnimChannel[] ogreChannel = new AnimChannel[100];
    protected AnimControl[] gladiatorControl = new AnimControl[100];
    protected AnimControl[] ogreControl = new AnimControl[100];
    
    NiftyJmeDisplay niftyDisplay;
    Nifty nifty;
    Element niftyElement;
     
    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayStatView(false);
        app.start();
        
    }
    
    
    @Override
    public void simpleInitApp() {
        // GUI 
        final MainMenuController cont = new MainMenuController(this);
        stateManager.attach(cont);
        
        niftyDisplay  = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty =  niftyDisplay.getNifty();
        
        guiViewPort.addProcessor(niftyDisplay);
       // nifty.setDebugOptionPanelColors(true);
        flyCam.setDragToRotate(true);
 
        nifty.loadStyleFile("nifty-default-styles.xml");
 
        nifty.loadControlFile("nifty-default-controls.xml");
        
        nifty.addScreen("Screen_ID", new ScreenBuilder("Hello Nifty Screen"){{
        controller(cont); // Screen properties
 
        // <layer>
        layer(new LayerBuilder("Layer_ID") {{
            childLayoutVertical(); // layer properties, add more...
 
            // <panel>
            panel(new PanelBuilder("Panel_ID") {{
               childLayoutCenter(); // panel properties, add more...
 
                // GUI elements
                /*control(new ButtonBuilder ("Button_ID", "Generate Building"){{
                    alignLeft();
                    valignCenter();
                    height("10%");
                    width("10%");
                    interactOnClick("next()");
 
                }});*/
                image(new ImageBuilder("louda") {{
                filename("Interface/Untitled.png");
                height("15%");
                width("10%");
                interactOnClick("nextR()");
                alignLeft();
                valignBottom();
            }});
 
               text(new TextBuilder("first") {{
                    text("GOLD: 200");
                    font("Interface/Fonts/Default.fnt");
                    height("20%");
                    width("20%");
                    alignLeft();
                    valignTop();
                }});
               text(new TextBuilder("second") {{
                    text("GOLD: 200");
                    font("Interface/Fonts/Default.fnt");
                    height("20%");
                    width("20%");
                    alignRight();
                    valignTop();
                }});
               text(new TextBuilder("win") {{
                    text("");
                    font("Interface/Fonts/Default.fnt");
                    height("100%");
                    width("100%");
                    alignCenter();
                    valignCenter();
                }});
               
               image(new ImageBuilder("louda2") {{
                filename("Interface/Untitled.png");
                height("15%");
                width("10%");
                interactOnClick("nextB()");
                alignRight();
                valignBottom();
            }});
 
                //.. add more GUI elements here
 
            }});
            // </panel>
          }});
        // </layer>
      }}.build(nifty));
    // </screen>
 
    nifty.gotoScreen("Screen_ID"); // start the screen
                 
        //ENVIRONMENT SETUP
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(180);
        cam.setLocation(new Vector3f(0f, 175f, 140f));
        //cam.setViewPort(0.1f, 0.9f, 0.1f, 0.9f);
        cam.setAxes(new Vector3f(-0.99998283f, -4.332047E-6f, -0.005864509f), new Vector3f(0.0048147123f, 0.5703341f, -0.82139874f), new Vector3f(0.0033482877f, -0.8214128f, -0.5703243f));
        Spatial scene = assetManager.loadModel("Models/Terrain/Terrain.j3o");
        DirectionalLight light = new DirectionalLight();
        rootNode.attachChild(scene);
        rootNode.addLight(light);
        
        //CASTLES
        castle1 = (Node) assetManager.loadModel("Models/Red - Castle/Red - Castle.j3o");
        castle2 = (Node) assetManager.loadModel("Models/Blue-Castle/Blue-Castle.j3o");
        castle1.setLocalTranslation(-90f, 0f, -6f);
        castle2.setLocalTranslation(90f, 0f, 7f);
        castle1.rotate(0,89.55f,0);
        castle2.rotate(0,-89.52f,0);
        castle1.setLocalScale(10f,10f,10f);
        castle2.setLocalScale(10f,10f,10f);
        rootNode.attachChild(castle1);
        rootNode.attachChild(castle2);
        
        //BUILDINGS
        building1 = (Node) assetManager.loadModel("Models/Building1/Building1.j3o");
        building2 = (Node) assetManager.loadModel("Models/Building2/Building2.j3o");
        
        //UNITS
        gladiator1 = (Node) assetManager.loadModel("Models/Blue Gladiator/Blue Gladiator.j3o");
        gladiator2 = (Node) assetManager.loadModel("Models/Blue Gladiator/Blue Gladiator.j3o");
        ogre = (Node) assetManager.loadModel("Models/Model-Ogre/Model-Ogre.j3o");
        //gladiatorControl = gladiator.getChild("cylinder1_copy21_default.001").getControl(AnimControl.class);
        //gladiatorControl.addListener(this);
        //gladiatorChannel = gladiatorControl.createChannel();
        //gladiatorChannel.setAnim("Run");
        //ogreControl = ogre.getChild("Cube").getControl(AnimControl.class);
        //ogreControl.addListener(this);
        //ogreChannel = ogreControl.createChannel();
        //ogreChannel.setAnim("Run");
        
        //TEST BOX
        Box box1 = new Box(2f,2f,2f);
        cube1 = new Geometry("box", box1);
        cube2 = new Geometry("box", box1);
        WayPointr1 = new Geometry("box", box1);
        WayPointr2 = new Geometry("box", box1);
        WayPointb1 = new Geometry("box", box1);
        WayPointb2 = new Geometry("box", box1);

        
        //MATERIALS
        Material mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setColor("Ambient", ColorRGBA.Red);
        mat1.setColor("Diffuse", ColorRGBA.Red);
        mat1.setColor("Specular", ColorRGBA.Red);
        mat1.setBoolean("UseMaterialColors", true);
        mat1.setFloat("Shininess", 5);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat2.setColor("Ambient", ColorRGBA.Blue);
        mat2.setColor("Diffuse", ColorRGBA.Blue);
        mat2.setColor("Specular", ColorRGBA.Green);
        mat2.setBoolean("UseMaterialColors", true);
        mat2.setFloat("Shininess", 5);
        gladiator1.setMaterial(mat1);
        gladiator2.setMaterial(mat2);
        ogre.setMaterial(mat2);
        cube1.setMaterial(mat1);
        cube2.setMaterial(mat2);
        WayPointr1.setMaterial(mat1);
        WayPointr2.setMaterial(mat1);
        WayPointb1.setMaterial(mat2);
        WayPointb2.setMaterial(mat2);
        
        //GUI
        cube3 = new Geometry("box", box1);
        cube3.setLocalTranslation(460f,450f,0f);
        cube3.setLocalScale(100f, 10f, 3f);
        cube3.setMaterial(mat2);
        
        //WAYPOINTS
        WayPointr1.setLocalTranslation(-65, 2, -25);
     
        //rootNode.attachChild(WayPointr1);
        WayPointr2.setLocalTranslation(-65, 2, 25);
        
        //rootNode.attachChild(WayPointr2);
        WayPointb1.setLocalTranslation(65, 2, -25);
       
        //rootNode.attachChild(WayPointb1);
        WayPointb2.setLocalTranslation(65, 2, 25);
        
        //rootNode.attachChild(WayPointb2);

        //ARRAY INITILIZATION
        for(i = 0; i < 100; i++)
        {
            isAlive[i] = false;
            fighting[i] = false;
            fightingCastle1[i] = false;
            fightingCastle2[i] = false;
            buildingExistR[i] = false;
            buildingExistB[i] = false;
            SpawnR[i] = false;
            SpawnB[i] = false;
            timers[i] = 0;
        }
        
        //INPUTS
        initKeys();
        
    }
    
    private void initKeys() {
        inputManager.addMapping("Create", new KeyTrigger(KeyInput.KEY_SPACE));
        
        ActionListener actionListener = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                
                if (name.equals("Create") && !keyPressed) { 
                    /*int x = 0;
                    int xDup = 0; // X DUPLICATE
                    while (buildingExist[x] == true)
                    {
                        x++;
                        xDup++;
                    }
                    
                    xDup = xDup%13; //IF A LINE OF BUILDINGS IS COMPLETE, START OVER
                    
                    System.out.println("Number of Buildings (X): " + (x+1));
                    System.out.println("Number of Buildings in Current Line (X Duplicate): " + (xDup+1));
                    
                    
                    if (xDup%13==0 && x!= 0)
                        team1BuildLine += 8;
                    
                    int z = ((xDup-6)* -10); //-60 + 10 UPDATE
                    
                    buildings[x] = building1.clone();
                    buildings[x].setLocalTranslation(team1BuildLine, 2, z);
                    //System.out.println(buildings[x].getLocalTranslation().getZ());
                    buildingExist[x] = true;
                    rootNode.attachChild(buildings[x]);   
                    // timers[x] = getTimer();*/
                }
            }
        };
        inputManager.addListener(actionListener, "Create");
    }
    
       
      //Element niftyElementGoldB = nifty.getScreen("Hello Nifty Screen").findElementById("second"); 
    @Override
    public void simpleUpdate(float tpf) {
        
        
        Element niftyElementGoldR = nifty.getScreen("Hello Nifty Screen").findElementById("first"); 
        Element niftyElementGoldB = nifty.getScreen("Hello Nifty Screen").findElementById("second");
        Element WIN = nifty.getScreen("Hello Nifty Screen").findElementById("win");
        Element Img = nifty.getScreen("Hello Nifty Screen").findElementById("louda");
        Element Img2 = nifty.getScreen("Hello Nifty Screen").findElementById("louda2");
        time+=tpf;
        
        //CASTLE STATE
        System.out.println("Castle 1 Under Attack: "+ castleRUA);
        System.out.println("Castle 2 Under Attack: "+ castleBUA);
        
        //CASTLE UNDER ATTACK
        
        
        /*for (int p = 0;p<10;p++)
        {
        System.out.println("Unit " + p + " is fighting castle 1: " + fightingCastle1[p]);
        System.out.println("Unit " + p + " is fighting castle 2: " + fightingCastle2[p]);
        }*/
        
        if (time > 10)
        {
            goldR += 10;
            goldB += 10;
            time = 0;
        }
            
        
        //CAMERA CHECK
        //System.out.println("Left: " + cam.getLeft() + " Up: " + cam.getUp() + " Direction: " + cam.getDirection());
        //System.out.println(inputManager.getCursorPosition().getX());
        //System.out.println(inputManager.getCursorPosition().getY());
        //System.out.println(inputManager.getAxisDeadZone());
        //System.out.println(settings.getHeight());
        //System.out.println(settings.getWidth());
        
        //System.out.println(cube3.getLocalTranslation());
        
        /*if(inputManager.getCursorPosition().getX() < 2)
        {
            
            System.out.println("Boundary");
        }*/
        
        
        for (int t = 0; t<100; t++)
        {
            timerNewR[t]++;
            timerNewB[t]++;
            
            //System.out.println("Building " + t + ": " + buildingExistR[t]);
            
            
            if (buildingExistR[t] == true)
            {
                //System.out.println("Timer " + t + ": " + timerNew[t] + "   " + tpf);
                /*if (timers[t].getTimeInSeconds() > 2)
                {
                    j=0;
                    Spawn[t] = true;
                    timers[t].reset();
                }*/
                
                if(timerNewR[t] > 200)
                {
                    //System.out.println("Spawn");
                    j=0;
                    SpawnR[t] = true;
                    timerNewR[t] = 0;
                }
            }
            
            if (buildingExistB[t] == true)
            {
                //System.out.println("Timer " + t + ": " + timerNew[t] + "   " + tpf);
                /*if (timers[t].getTimeInSeconds() > 2)
                {
                    j=0;
                    Spawn[t] = true;
                    timers[t].reset();
                }*/
                
                if(timerNewB[t] > 200)
                {
                    j=0;
                    SpawnB[t] = true;
                    timerNewB[t] = 0;
                }
            }
        }
        
        //SPAWN
        
        for (k=0; k<100; k++)
        {
            if(SpawnR[k] == true)
            {
                while(isAlive[j]==true && j<100)
                {
                    j++;
                }
                
                teamPos = team1Pos;
                teamDir = team1Dir;
                team[j] = 0;
                
                
                cubes[j] = (Node) gladiator1.clone();
                gladiatorControl[j] = cubes[j].getChild("cylinder1_copy21_default.001").getControl(AnimControl.class);
                gladiatorChannel[j] = gladiatorControl[j].createChannel();
                gladiatorChannel[j].setAnim("Run");
                
                //HERE
                //cubes[j].move(cas)
                
                cubes[j].lookAt(castle2.getLocalTranslation(), Vector3f.UNIT_Y);
                HP[j] = 50;
                cubes[j].setLocalTranslation(buildingsR[k].getLocalTranslation());
                //timers[j] += tpf;
       

                
                //cubes[j].setLocalTranslation(teamPos, 5, 0);
                
                rootNode.attachChild(cubes[j]);
                isAlive[j] = true;
                SpawnR[k] = false;
            }
        
            if(SpawnB[k] == true)
            {
                while(isAlive[j]==true && j<100)
                {
                    j++;
                }
                
                teamPos = team2Pos;
                teamDir = team2Dir;
                team[j] = 1;
                

                
                if (team[j] == 1)
                {
                    cubes[j] = (Node) gladiator2.clone();
                    gladiatorControl[j] = cubes[j].getChild("cylinder1_copy21_default.001").getControl(AnimControl.class);
                    gladiatorChannel[j] = gladiatorControl[j].createChannel();
                    gladiatorChannel[j].setAnim("Run");
                    cubes[j].lookAt(castle1.getLocalTranslation(), Vector3f.UNIT_Y);
                    HP[j] = 50;
                    cubes[j].setLocalTranslation(buildingsB[k].getLocalTranslation());
                    //timers[j] += tpf;
                }

                
                //cubes[j].setLocalTranslation(teamPos, 5, 0);
                
                rootNode.attachChild(cubes[j]);
                isAlive[j] = true;
                SpawnB[k] = false;
            }
        }
   
        
 
        //BUILDING [T] SPAWNS
        
        /*if (myTimer.getTimeInSeconds() > 2)
        {
            j=0;
            Spawn = true;
            myTimer.reset();
        }*/
        
        
        
        //FIGHTING
       for (int x = 0; x < 100; x++)
       {
           //System.out.println(x + " is alive: " + isAlive[x]);
           
           for (int y = 0; y<100 ; y++)
           {         
               
               
               if (isAlive[x] == true && isAlive[y] == true && y!=x && team[x] != team[y])
               {
                   //cubes[x].move(cubes[y].getLocalTranslation().subtract(cubes[x].getLocalTranslation()).normalize().mult(tpf));
                   //cubes[y].move(cubes[x].getLocalTranslation().subtract(cubes[y].getLocalTranslation()).normalize().mult(tpf));
                        
                   if((cubes[x].getLocalTranslation().getX() - cubes[y].getLocalTranslation().getX()) < 5 && (cubes[x].getLocalTranslation().getX() - cubes[y].getLocalTranslation().getX()) > -5
                          && (cubes[x].getLocalTranslation().getZ() - cubes[y].getLocalTranslation().getZ()) < 9 && (cubes[x].getLocalTranslation().getZ() - cubes[y].getLocalTranslation().getZ()) > -9)
                   {
                        
                        fighting[x] = true;
                        fighting[y] = true;
                        cubes[x].lookAt(cubes[y].getLocalTranslation(), Vector3f.UNIT_Y);
                        cubes[y].lookAt(cubes[x].getLocalTranslation(), Vector3f.UNIT_Y);
                        timers[x] += tpf;
                        timers[y] += tpf;
                                         
                        

                        //System.out.println(cubes[x].getLocalTranslation().getZ() - cubes[y].getLocalTranslation().getZ());
                        //System.out.println("Collided");
                   }
               }
               
                if(timers[x] > 1 && isAlive[x] == true && fighting[x] == true)
                {
                    gladiatorChannel[x].setAnim("Fight");
                    gladiatorChannel[y].setAnim("Fight");
                    if(HP[y] == 0)
                    {
                        fightingCastle1[y] = false;
                        fightingCastle1[x] = false;
                        fightingCastle2[x] = false;
                        fightingCastle2[y] = false;
                        goldR += 10;
                        isAlive[y] = false;
                        fighting[y] = false;
                        fighting[x] = false;
                        rootNode.detachChild(cubes[y]);
                        gladiatorChannel[x].setAnim("Run");
                        for (int p = 0; p < 100; p++)
                        {
                            
                            if(isAlive[p] == true)
                            {
                                gladiatorChannel[p].setAnim("Run");
                            }
                            fighting[p] = false;
                            if(fightingCastle1[p] == true)
                            {
                                tempFlagR = true;
                            }
                        }
                        
                        if (tempFlagR == false)
                            castleRUA = false;
                        
                    }
                    HP[y]-=10;
                    timers[x] = 0; //WORKS FOR ONLY ONE
                }
                
                        
                if(timers[y] > 1 && isAlive[y] == true && fighting[y] == true)
                {
                    gladiatorChannel[x].setAnim("Fight");
                    gladiatorChannel[y].setAnim("Fight");
                    if(HP[x] == 0)
                    {
                        goldB += 10;
                        isAlive[x] = false;
                        fighting[x] = false;
                        fighting[y] = false;
                        fightingCastle1[y] = false;
                        fightingCastle1[x] = false;
                        fightingCastle2[x] = false;
                        fightingCastle2[y] = false;
                        rootNode.detachChild(cubes[x]);
                        gladiatorChannel[y].setAnim("Run");
                        //System.out.println(y + "is fighting: " + fighting[y]);
                        for (int p = 0; p < 100; p++)
                        {
                            if(isAlive[p] == true)
                            {
                                gladiatorChannel[p].setAnim("Run");
                            }
                            fighting[p] = false;
   
                        }
                  
                    }
                    HP[x]-=10;
                    timers[y] = 0; //WORKS FOR ONLY ONE
                }
           }
           
           if(isAlive[x] == true && (cubes[x].getLocalTranslation().getX() - castle2.getLocalTranslation().getX()) < 8 && (cubes[x].getLocalTranslation().getX() - castle2.getLocalTranslation().getX()) > -8
                   && (cubes[x].getLocalTranslation().getZ() - castle2.getLocalTranslation().getZ()) < 11f && (cubes[x].getLocalTranslation().getZ() - castle2.getLocalTranslation().getZ()) > -11f
                   && team[x] == 0)
           {
               
               timers[x] += tpf;
               fighting[x] = true;
               fightingCastle2[x] = true;
               castleBUA = true;
               
                if(timers[x] > 1 && isAlive[x] == true && fighting[x] == true)
                {
                    gladiatorChannel[x].setAnim("Fight");
                    if(castle2HP == 0)
                    {
                        System.out.println("Castle 2 Destroyed");
                        rootNode.detachChild(castle2);
                        WIN.getRenderer(TextRenderer.class).setText("RED TEAM WINS");
                        Img.setVisible(false);
                        Img2.setVisible(false);
                    }
                    
                    //System.out.println(castle2HP);
                    castle2HP -= 10;
                    timers[x] = 0;
                    fighting[x] = false;
                }
           }
           
           if(isAlive[x] == true && (cubes[x].getLocalTranslation().getX() - castle1.getLocalTranslation().getX()) < 8 && (cubes[x].getLocalTranslation().getX() - castle1.getLocalTranslation().getX()) > -8
                   && (cubes[x].getLocalTranslation().getZ() - castle1.getLocalTranslation().getZ()) < 11f && (cubes[x].getLocalTranslation().getZ() - castle1.getLocalTranslation().getZ()) > -11f
                   && team[x] == 1)
           {
               
               timers[x] += tpf;
               fighting[x] = true;
               fightingCastle1[x] = true;
               castleRUA = true;
               
                if(timers[x] > 1 && isAlive[x] == true && fighting[x] == true)
                {
                    gladiatorChannel[x].setAnim("Fight");
                    if(castle1HP == 0)
                    {
                        System.out.println("Castle 1 Destroyed");
                        rootNode.detachChild(castle1);
                        WIN.getRenderer(TextRenderer.class).setText("BLUE TEAM WINS");
                        Img.setVisible(false);
                        Img2.setVisible(false);
                        
                    }
                    //System.out.println(castle2HP);
                    castle1HP -= 10;
                    timers[x] = 0; 
                    fighting[x] = false;
                }
           }
           
           
           if (isAlive[x] == true && team[x] == 0 && fighting[x] == false)
           {
               if(castleRUA == true && (cubes[x].getLocalTranslation().getX() - castle1.getLocalTranslation().getX()) < 20 && (cubes[x].getLocalTranslation().getX() - castle1.getLocalTranslation().getX()) > -20)
               {
                   cubes[x].lookAt(castle1.getLocalTranslation(), Vector3f.UNIT_Y);
                   cubes[x].move(castle1.getLocalTranslation().subtract(cubes[x].getLocalTranslation()).normalize().mult(tpf*10));
               }
               //cubes[x].rotate(0f,0f,-0.02f);
               cubes[x].lookAt(castle2.getLocalTranslation(), Vector3f.UNIT_Y);
               // cubes[x].move(0.2f,0f,0f);
               cubes[x].move(castle2.getLocalTranslation().subtract(cubes[x].getLocalTranslation()).normalize().mult(tpf*10));
           }
           
           else if (isAlive[x] == true && team[x] == 1 && fighting[x] == false)
           {
               if(castleBUA == true && (cubes[x].getLocalTranslation().getX() - castle2.getLocalTranslation().getX()) < 20 && (cubes[x].getLocalTranslation().getX() - castle2.getLocalTranslation().getX()) > -20)
               {
                   cubes[x].lookAt(castle2.getLocalTranslation(), Vector3f.UNIT_Y);
                   cubes[x].move(castle2.getLocalTranslation().subtract(cubes[x].getLocalTranslation()).normalize().mult(tpf*10));
               }
               //cubes[x].rotate(0f,0f,0.02f);
               cubes[x].lookAt(castle1.getLocalTranslation(), Vector3f.UNIT_Y);
               //cubes[x].move(-0.2f,0f,0f);
               cubes[x].move(castle1.getLocalTranslation().subtract(cubes[x].getLocalTranslation()).normalize().mult(tpf*10));
           }
            
       }
       
       for (int l = 0; l<100; l++)
        {
            fightingCastle2[l] = false;
            fightingCastle1[l] = false;
            
        }
       
            castleBUA = false;
            castleRUA = false;
       
       String tempR ="Gold : " ;
       tempR +=  Integer.toString(goldR);
       
            String tempB="Gold : ";
            tempB +=  Integer.toString(goldB);
       niftyElementGoldR.getRenderer(TextRenderer.class).setText(tempR);
        niftyElementGoldB.getRenderer(TextRenderer.class).setText(tempB);
    }
       

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
