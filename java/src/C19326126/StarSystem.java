package C19326126;

import processing.core.PApplet;

public class StarSystem extends PApplet{
    //rotation and star generation bool
    float rotation;
    float rotation2;
    float rotation3;
    float rotation4;
    float rotation5;
    Boolean generation = false;
    boolean Setting = true;

    // rings
    float w = 40;
    float h = 40;
    float r;
    float arcLength;
    float total = 30;

    //Star Coordinates & Sizes
    float x [] = new float[200];
    float y [] = new float[200]; 
    float z [] = new float[200];
    float size[] = new float[200];
    float x2 [] = new float[200];
    float y2 [] = new float[200]; 
    float z2 [] = new float[200];
    float size2[] = new float[200];

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

    }

    public void draw(){
        int arr[] = {320, 150, 225, 425, 520, 650, 762, 835};
        noCursor();        
        background(0);
        lights();
        camera(mouseX*2, mouseY, (height/2) / tan(PI/5), width/2, height/2, 0, 0, 1, 0);

        if(keyPressed){
            if(key == 'w' || key == 'W'){
                rotation += .15;
                rotation2 += .85;
                rotation3 += 1.5;
                rotation4 += .65;
                rotation5 -= .1;
            }
            else if(key == 's' || key == 'S'){
                rotation -= .30;
                rotation2 -= 1.70;
                rotation3 -= 3;
                rotation4 -= 1.30;
                rotation5 += .2;
            }
            else if(key == 'q' || key == 'Q'){
                Setting = true;
            }
            else if(key == 'e' || key == 'E'){
                Setting = false;
            }
        }

        pushMatrix();//Sun
            translate(width/2, height/2);
            rotate(radians(rotation));
            Sun1.display();

            for(int k = 0; k < 360; k+=10){
                
            }
    
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
            StarField();

        popMatrix(); //end star genertaion
        if(Setting == true){
            pushMatrix();
                translate(width/2, height/2);
                Paths();
            popMatrix();
        }
        else{
            for(int i = 0; i < arr.length; i++){
                int k = arr[i];
                Rings(k);
            }
        }   

        //rotational constants for different planets
        rotation += .15;
        rotation2 += .85;
        rotation3 += 1.5;
        rotation4 += .65;
        rotation5 -= .1;
    }

    private void Mercury() {
        pushMatrix();
        rotate(radians(rotation));
        translate(-150, -10);
        Sun7.display();
        popMatrix();
    }//End Mercury

    private void Venus(){
        pushMatrix();
        rotate(radians(rotation));
        translate(220, 40);
        Sun10.display();
        popMatrix();
    }


    private void Earth() {
        pushMatrix();//Earth
            rotate(radians(rotation2));
            
            translate(320, 0);
            Sun2.display();
            rotate(radians(rotation2));
                pushMatrix(); //Moon
                    translate(40, 0);
                    Sun3.display();
                popMatrix(); //End Moon
            popMatrix(); //End earth
    }//End Earth


    void Mars(){
        pushMatrix();
        translate(425,0);
        rotate(radians(rotation4));
        Sun4.display();//Mars
            pushMatrix(); //Mars Moons
                rotate(radians(rotation3));
                translate(29, 0);
                Sun5.display();
            popMatrix();
            pushMatrix();
                rotate(radians(rotation));
                translate(40, 15);
                Sun5.display();
            popMatrix(); //End Mars Moon
        popMatrix(); //End Mars
    }//End Mars

    private void Jupiter() {
        pushMatrix();
        rotate(radians(radians(rotation5)));
        translate(-520, 30);
        Sun8.display();
        popMatrix();
    }

    private void Saturn(){
        pushMatrix();
        rotate(radians(radians(rotation4)));
        translate(640, -100);
        Sun9.display();
        for(int j = 80; j <120; j++){
            noFill();
            stroke(255,215,140);
            ellipse(0, 0, j, j);
    }
        popMatrix();
    }

    private void Uranus(){
        pushMatrix();
        rotate(radians(rotation3));
        translate(760, 70);
        Sun11.display();
        popMatrix();
    }

    private void Neptune(){
        pushMatrix();
        rotate(radians(rotation2));
        translate(830,90);
        Sun12.display();
        popMatrix();
    }

    private void Paths(){
        pushMatrix();
            noFill();
            stroke(255);
            strokeWeight((float) .8);
            rotate(radians(rotation));
            ellipse(0,0, 640, 640);
            ellipse(0,0, 300, 300);
            ellipse(0,0, 450, 450);
            ellipse(0,0, 850, 850);
            ellipse(0,0, 1040, 1040);
            ellipse(0,0, 1300, 1300);
            ellipse(0,0, 1525, 1525);
            ellipse(0,0, 1670, 1670);

            pushMatrix();
                rotate(radians(rotation2));
                translate(320,0);
                noFill();
                stroke(255);
                strokeWeight((float) .8);
                ellipse(0,0, 80, 80);
            popMatrix();

            pushMatrix();
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
                //Blue color
                fill(80,160,255);
                //Squares drawn by center of their coordinates
                rectMode(CENTER);
                rect(0,0,15,1);
            popMatrix();        
        }
        popMatrix();
    }


    private void StarField(){
        if(!generation){
            
        //seperate i's with if statements to be able to make stars of diff audio bands
        //eg if i > 25 &&i < 50 stroke map diffcolor * bands.length


        for(int i = 0; i < 200; i++){
            x[i] = random(-3500,3500);
            y[i] = random(-3000,3000);
            z[i] = random(50,3000);
            size[i] = random(3,18);
        }
        for(int j = 0; j < 200; j++){
            x2[j] = random(-3500,3500);
            y2[j] = random(-3000,3000);
            z2[j] = random(-3000,-50);
            size2[j] = random(3,18);
        }

        
        generation = true;
        }
        for(int i = 0; i < 100; i++){
            //Z values ensure Planets won't strike a star
            pushMatrix();
            translate(x[i], y[i], z[i]);
            fill(255,0,0);
            sphere(size[i]);
            popMatrix();
        }
        for(int i = 100; i < 200; i++){
            //Z values ensure Planets won't strike a star
            pushMatrix();
            translate(x[i], y[i], z[i]);
            fill(120,120,255);
            sphere(size[i]);
            popMatrix();
        }
        for(int j = 0; j < 100; j++){
            pushMatrix();
            translate(x2[j], y2[j], z2[j]);
            fill(255,0,0);
            sphere(size2[j]);
            popMatrix();
        }
        for(int j = 100; j < 200; j++){
            pushMatrix();
            translate(x2[j], y2[j], z2[j]);
            fill(120,120,255);
            sphere(size2[j]);
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

        public void display(){
            //For loops for music and changing stroke and size
            noStroke();
            fill(c);
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