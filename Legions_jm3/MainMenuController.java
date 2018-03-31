/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyMouseInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;

public class MainMenuController extends AbstractAppState implements ScreenController {
 private  Node rootNode;
  private Application app;
  private AppStateManager stateManager;
  private Nifty nifty;
  private Screen screen;
  private AssetManager assetManager;
  private Node nd ,building1;
  private float x = 0 , y = 0 , z = -10 ; 
  Main k = new Main();
  
  public MainMenuController(SimpleApplication app) {
      
      this.rootNode = app.getRootNode();
      this.assetManager = app.getAssetManager();
      nd = (Node) assetManager.loadModel("Models/Building1/Building1.j3o");
      building1 = (Node) assetManager.loadModel("Models/Building1/Building1.j3o");
      
      
  }
   @Override
  /*  public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        
            
    }
*/

  public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
  }


  @Override
    public void onStartScreen() {
        //screen.findElementById("Button_ID").getRenderer()
       // eHintText = screen.findElementById(string)
  }
       
  @Override
    public void onEndScreen() {
  }
    public void nextR() {
                    if (Main.goldR >= 150)
                    {
                    (Main.goldR)-=150;
                    int x = 0;
                    int xDup = 0; // X DUPLICATE
                    while (Main.buildingExistR[x] == true)
                    {
                        x++;
                        xDup++;
                    }
                    
                    xDup = xDup%13; //IF A LINE OF BUILDINGS IS COMPLETE, START OVER
                    
                    System.out.println("Number of Buildings (X): " + (x+1));
                    System.out.println("Number of Buildings in Current Line (X Duplicate): " + (xDup+1));
                    
                    
                    if (xDup%13==0 && x!= 0)
                        Main.team1BuildLine += 8;
                    
                    int z = ((xDup-6)* -10); //-60 + 10 UPDATE
                    
                    Main.buildingsR[x] = building1.clone();
                    Main.buildingsR[x].setLocalTranslation(Main.team1BuildLine, 2, z);
                    //System.out.println(buildings[x].getLocalTranslation().getZ());
                    Main.buildingExistR[x] = true;
                    rootNode.attachChild(Main.buildingsR[x]);   
                    // timers[x] = getTimer(); 
                    }
    }
    public void nextB() {
                    if (Main.goldB >= 150)
                    {
                    Main.goldB -= 150;
                    int x = 0;
                    int xDup = 0; // X DUPLICATE
                    while (Main.buildingExistB[x] == true)
                    {
                        x++;
                        xDup++;
                    }
                    
                    xDup = xDup%13; //IF A LINE OF BUILDINGS IS COMPLETE, START OVER
                    
                    System.out.println("Number of Buildings (X): " + (x+1));
                    System.out.println("Number of Buildings in Current Line (X Duplicate): " + (xDup+1));
                    
                    
                    if (xDup%13==0 && x!= 0)
                        Main.team1BuildLine -= 8;
                    
                    int z = ((xDup-6)* -10); //-60 + 10 UPDATE
                    
                    Main.buildingsB[x] = building1.clone();
                    Main.buildingsB[x].setLocalTranslation(Main.team2BuildLine, 2, z);
                    //System.out.println(buildings[x].getLocalTranslation().getZ());
                    Main.buildingExistB[x] = true;
                    rootNode.attachChild(Main.buildingsB[x]);   
                    // timers[x] = getTimer();
                    }
    }
    
    public void nextWithCoords(int x, int y) {
        System.out.println("next() clicked at: " + x + ", " + y);
 }
    public void tete(Element element , NiftyMouseInputEvent event)
    {
        
    }
    public void constructBuilding()
    {
        
    }

}