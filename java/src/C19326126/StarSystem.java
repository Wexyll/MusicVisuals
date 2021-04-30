package C19326126;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import peasy.*;

public class StarSystem extends Visual{
    //rotation and star generation bool
    float rotation;
    float rotation2;
    float rotation3;
    float rotation4;
    float rotation5;
    float rotation6;
    float rotation7;
    Boolean generation = false;
    boolean Setting = true;

    //Music
    Minim minim; // Connect to minim
    AudioPlayer ap;
    AudioBuffer ab; // Samples
    float[] lerpedBuffer;

    // rings
    float w = 40;
    float h = 40;
    float r;
    float arcLength;
    float total = 30;
    float RingSize=0;

    //Star Coordinates & Sizes
    float x [] = new float[200];
    float y [] = new float[200]; 
    float z [] = new float[200];
    float size[] = new float[200];
    float x2 [] = new float[200];
    float y2 [] = new float[200]; 
    float z2 [] = new float[200];
    float size2[] = new float[200];
    float StarSize = 0;
    float StarSizeSmoothed = 0;
    float FillColor = 0;
    float FillColor2 = 5;
    float SysColor = 0;
    Boolean ColorBool;
    Boolean ColorBool2;
    Boolean PlanetColor = true;
    Boolean PlanetBool = true;
    PeasyCam cam;

    //stars, planets and suns
    Sun Sun1, Sun2, Sun3, Sun4, Sun5, Sun6, Sun7, Sun8, Sun9, Sun10, Sun11, Sun12;
    


    public void settings(){
        fullScreen(P3D, SPAN);
        //color, position X/Y, size
        Sun1 = new Sun(color(255, 255, 0), 0, 0, 50);//Sun
        Sun2 = new Sun(color(0, 255, 0), 10, 0, 20);//Earth
        Sun3 = new Sun(color(0, 0, 255), 10, 0, 5);//Moon
        Sun4 = new Sun(color(255, 145, 70), 10, 0, 18);//Mars
        Sun5 = new Sun(color(255, 0, 50), 10, 0, 4);//Moon - Mars
        Sun6 = new Sun(color(255, 0, 50), 10, 0, 6);//Moon2 - Mars
        Sun7 = new Sun(color(200, 100, 20), 10, 0, 15);//Mercury
        Sun8 = new Sun(color(200,180,40), 10, 0, 35);//Jupiter
        Sun9 = new Sun(color(255,215,140), 10, 0, 28);//Saturn
        Sun10 = new Sun(color(255,0,0), 10, 0, 16);//Venus
        Sun11 = new Sun(color(10,100,255), 10, 0, 18);//Uranus
        Sun12 = new Sun(color(40,190,255), 10, 0, 14);//Neptune
    }


    public void setup(){
        startMinim();
        colorMode(HSB, 360, 100, 100);
        loadAudio("KLOUD.mp3");
        //Camera pointing to the middle of the system
        cam = new PeasyCam(this, width/2,height/2,0,1500);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(4000); 
    }

    public void draw(){
        rotateX((float) -.5);
        rotateY((float) -.5);

        //Calling frequency bands from visuals file
        calculateFrequencyBands();
        calculateAverageAmplitude(); 
        try
        {
            calculateFFT(); //acquire frequency 
        }
        catch(VisualException e)
        {
            e.printStackTrace();
        }

        //Array for location values of the planets
        int arr[] = {320, 150, 225, 425, 520, 650, 762, 835};
        noCursor();        
        background(0);
        lights();

        //Old camera idea before using PeasyCam
        //camera(mouseX*2, mouseY, (height/2) / tan(PI/5), width/2, height/2, 0, 0, 1, 0);

        if(keyPressed){
            if(key == 'w' || key == 'W'){ //Speed up rotation
                rotation += .15;
                rotation2 += .85;
                rotation3 += 1.5;
                rotation4 += .65;
                rotation5 -= .1;
            }
            else if(key == 's' || key == 'S'){ //Slow rotation
                rotation -= .30;
                rotation2 -= 1.70;
                rotation3 -= 3;
                rotation4 -= 1.30;
                rotation5 += .2;
            }
            else if(key == 'q' || key == 'Q'){ //Q changes ring setting
                Setting = true;
            }
            else if(key == 'e' || key == 'E'){ //Changes ring setting as well
                Setting = false;
            }
            else if(key == '1'){ //Changes planet color
                PlanetColor = false;
            }
            else if(key == '2'){ //Default planet color
                PlanetColor = true;
            }
            else if (key == ' '){ //Play the song
                getAudioPlayer().cue(0);
                getAudioPlayer().play();
            }
        }

        pushMatrix();//Sun
            translate(width/2, height/2);
            //Default rotation of all planets
            rotate(radians(rotation));
            //Displaying the sun
            Sun1.display(30, 60);

            //Begin placing planets

            Mercury(); //-150, -10

            Venus(); //220, -40

            Earth(); //320, 0

            Mars();//425, 0

            Jupiter(); //-520, -30

            Saturn(); //640, -100

            Uranus(); // 760, 70

            Neptune(); //830, 90


        popMatrix(); //end Sun

        pushMatrix(); //begin star generation

            translate(width/2, height/2);
            //Calling the random generation of stars
            StarField();

        popMatrix(); //end star genertaion

        //Original white paths for planets
        if(Setting == true){
            pushMatrix();
                translate(width/2, height/2);
                for(int i = 0; i < arr.length; i++){
                    int k = arr[i];
                    Paths(k); //Displaying rings in alternate format
                }
            popMatrix();
        }
        else{
            for(int i = 0; i < arr.length; i++){
                int k = arr[i];
                Rings(k); //Displaying rings in alternate format
            }
        }   

        //rotational constants for different planets
        rotation += .15;
        rotation2 += .55;
        rotation3 += 1.5;
        rotation4 += .1;
        rotation5 -= .05;
        rotation6 -= .07;
        rotation7 -= .08;
    }

    private void Mercury() {
        pushMatrix();
        rotate((radians(rotation3)));
        translate(-150, -10);
        Sun7.display(0, 25);
        popMatrix();
    }//End Mercury

    private void Venus(){
        pushMatrix();
        rotate((float) (radians(rotation2)+ .2));
        translate(220, 40);
        Sun10.display(15, 35);
        popMatrix();
    }//End venus


    private void Earth() {
        pushMatrix();//Earth
            rotate(radians(rotation2));

            translate(320, 0);
            Sun2.display(90, 150);
            rotate(radians(rotation2));
                pushMatrix(); //Moon
                    translate(40, 0);
                    Sun3.display(190,210);
                popMatrix(); //End Moon
            popMatrix(); //End earth
    }//End Earth


    void Mars(){
        pushMatrix();
        translate(425,0);
        rotate(radians(rotation));
        Sun4.display(0, 25);//Mars
            pushMatrix(); //Mars Moons
                rotate(radians(rotation3));
                translate(29, 0);
                Sun5.display(0, 25);
            popMatrix();
            pushMatrix();
                rotate(radians(rotation));
                translate(40, 15);
                Sun5.display(0, 25);
            popMatrix(); //End Mars Moon
        popMatrix(); //End Mars
    }//End Mars

    private void Jupiter() {
        pushMatrix();
        rotate(radians((radians(rotation5))));
        translate(-520, 30);
        Sun8.display(20, 50);
        popMatrix();
    }

    private void Saturn(){
        pushMatrix();
        rotate(radians(radians(rotation4)));
        translate(640, -100);
        Sun9.display(15, 40);
        for(int j = 80; j <120; j++){
            noFill();
            stroke(52,50,50);
            ellipse(0, 0, j, j);
        } //End saturn rings
        popMatrix();
    }//End Saturn

    private void Uranus(){
        pushMatrix();
        rotate(radians(rotation6));
        translate(760, 70);
        Sun11.display(170, 190);
        popMatrix();
    }//End Uranus

    private void Neptune(){
        pushMatrix();
        rotate(radians(rotation7));
        translate(830,90);
        Sun12.display(210, 240);
        popMatrix();
    }//Neptune

    //Begin the white paths
    private void Paths(int x){
        
        pushMatrix();
            noFill();
            stroke(255);
            strokeWeight((float) .8);
            rotate(radians(rotation));
            //Calling elipses and placing them in the planets path
            for(int i = 0; i < total; i++)
            {
            ellipse(0,0, x*2, x*2);     
            }

            pushMatrix();//Earths ring for the moons path
                rotate(radians(rotation2));
                translate(320,0);
                noFill();
                stroke(255);
                strokeWeight((float) .8);
                ellipse(0,0, 80, 80);
            popMatrix();

            pushMatrix();//Mars' moons rings
                translate(425,0);
                noFill();
                stroke(255);
                strokeWeight((float) .8);
                ellipse(0,0, 58, 58);//Mars moon1
                ellipse(0,0, 85, 85);//Mars moon2
            popMatrix();

        popMatrix();
    }

    private void Rings(int x){
        pushMatrix();
        translate(width/2, height/2);
        //rotate the rings against the rotation of the planets
        rotate(radians(-rotation));
        r = x; //Inputted radius of the planets arc from Arr[]
        arcLength = 0;
        for(int i = 0; i < total; i++){
            //each rect will be placed every 12 degree's
            arcLength += 12;
            //the radians of how much the plane will be rotated
            float theta = radians(arcLength);

            pushMatrix();
                //rotation of grid plane to place the boxes on the new rotated cartesian coordinate plane
                translate(r*cos(theta), r*sin(theta));

                //this will rotate the boxes
                rotate(theta);
                //Color
                fill(map(getAmplitude()/2, 0, 1, 0, 255), 255, 255);
                //Squares drawn by center of their coordinates
                rectMode(CENTER);
                float RS = 10 + (getAmplitude() * 300);
                RingSize = lerp((float) (RingSize/1.15), RS, 0.1f);
                rect(0,0,RingSize,1);
            popMatrix();        
        }
        popMatrix();
    }


    private void StarField(){
        if(!generation){
        
        //Range values for where stars can generate
        for(int i = 0; i < 200; i++){
            x[i] = random(-3500,3500);
            y[i] = random(-3000,3000);
            z[i] = random(150,3000);
            size[i] = random(3,10);
        }
        for(int j = 0; j < 200; j++){
            x2[j] = random(-3500,3500);
            y2[j] = random(-3000,3000);
            z2[j] = random(-3000,-150);
            size2[j] = random(3,8);
        }

        //Makes the stars only generate once
        generation = true;
        }
        for(int i = 0; i < 100; i++){
            pushMatrix(); //Beging generating first set of stars
            StarSize = size[i] + (getAmplitude() * 100);
            StarSizeSmoothed = lerp(StarSizeSmoothed, StarSize/2, 0.00125f);
            translate(x[i], y[i], z[i]);
            for(int o = 0; o < getSmoothedBands().length; o++){
                fill(map(o, 0, getBands().length * FillColor, 255, 0), 255, 255);
                if( FillColor >= 5){
                    ColorBool = false;
                }
                else if(FillColor <= 0){
                    ColorBool = true;
                }
                if(ColorBool == true){ //if it's reached zero it will increment to 5
                    FillColor += getBands().length * .0000006f;
                }
                else if (ColorBool == false){ //it's reached 5 it will decrement back to 0
                    FillColor -= getBands().length * .0000006f;
                }
            }
            
            sphere(StarSizeSmoothed);          
            popMatrix();
        }
        for(int i = 100; i < 200; i++){
            pushMatrix(); //Second set of stars
            StarSize = size[i] + (getAmplitude() * 150);
            StarSizeSmoothed = lerp(StarSizeSmoothed, StarSize/2, 0.0025f);
            translate(x[i], y[i], z[i]);
            for(int o = 0; o < getSmoothedBands().length; o++){
                fill(map(o, 0, getBands().length * FillColor2, 255, 0), 255, 255);
                if( FillColor2 >= 5){
                    ColorBool2 = false;
                }
                else if(FillColor2 <= 0){
                    ColorBool2 = true;
                }
                if(ColorBool2 == true){
                    FillColor2 += getBands().length * .0000004f;
                }
                else if (ColorBool2 == false){
                    FillColor2 -= getBands().length * .0000004f;
                }
            }
            sphere(StarSizeSmoothed);
            popMatrix();
        }
        for(int j = 0; j < 100; j++){
            pushMatrix();//Third set of stars
            StarSize = size[j] + (getAmplitude() * 100);
            StarSizeSmoothed = lerp(StarSizeSmoothed, StarSize/2, 0.00125f);
            translate(x2[j], y2[j], z2[j]);
            for(int o = 0; o < getSmoothedBands().length; o++){
                fill(map(o, 0, getBands().length * FillColor, 255, 0), 255, 255);
                if( FillColor >= 5){
                    ColorBool = false;
                }
                else if(FillColor <= 0){
                    ColorBool = true;
                }
                if(ColorBool == true){
                    FillColor += getBands().length * .0000006f;
                }
                else if (ColorBool == false){
                    FillColor -= getBands().length * .0000006f;
                }
            }
            sphere(StarSizeSmoothed);
            popMatrix();
        }
        for(int j = 100; j < 200; j++){
            pushMatrix();//Fourth set of stars
            StarSize = size[j] + (getAmplitude() * 150);
            StarSizeSmoothed = lerp(StarSizeSmoothed, StarSize/2, 0.0025f);
            translate(x2[j], y2[j], z2[j]);
            for(int o = 0; o < getSmoothedBands().length; o++){
                fill(map(o, 0, getBands().length * FillColor2, 255, 0), 255, 255);
                if( FillColor2 >= 5){
                    ColorBool2 = false;
                }
                else if(FillColor2 <= 0){
                    ColorBool2 = true;
                }
                if(ColorBool2 == true){
                    FillColor2 += getBands().length * .0000004f;
                }
                else if (ColorBool2 == false){
                    FillColor2 -= getBands().length * .0000004f;
                }
            }
            sphere(StarSizeSmoothed);
            popMatrix();
        }
    }//end StarField

    public class Sun{
        int c;
        int s;
        float xpos = 0;
        float ypos = 0;

        Sun(int tempC, float tempXpos, float tempYpos, int size){
            c = tempC;
            xpos= tempXpos;
            ypos= tempYpos;
            s = size;
        }//End Sun

        public void display(int X, int Y){
            float Z = 100;
            //For loops for music and changing stroke and size
            noStroke();
            if(PlanetColor == true){ //The user can choose for default planet colors or for planets that change color
                fill(c);
            }else{
                for(int o = 0; o < getSmoothedBands().length; o++){//X , Y 
                    fill(map(o, 0, getBands().length * SysColor, X, Y), Z, 100);
                    if(SysColor >= 5){
                        PlanetBool = false;
                    }
                    else if(SysColor <= 1){
                        PlanetBool = true;
                    }
                    if(PlanetBool == true){
                        SysColor += getBands().length * .00004f;
                    }
                    else if (PlanetBool == false){
                        SysColor -= getBands().length * .00004f;
                    }
                    if(Z >= 100){ //Changing the values for saturation
                        Z = (float) (Z - .05);
                    }
                    if(Z <= 60){
                        Z = (float) (Z + .05);
                    }
                }   
            }       
            sphere(s);
        }//End display
    }
}

/*
for(int j = 80; j <120; j++){
            noFill();
            rotateY(radians(rotation));
            stroke(255,215,140);
            ellipse(0, 0, j, j);
    }
    */
    //when the user presses Q make this begin to happen around the sun