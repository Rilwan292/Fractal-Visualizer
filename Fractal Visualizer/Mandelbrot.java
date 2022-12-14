/*
    Author:
    M.M.M.RILWAN
    E/17/292
    
*/
public final class Mandelbrot extends JPExtend implements Runnable {
    private double realMin, realMax, complexMin, complexMax;
    private int iterations, heightStart, heightEnd;

    public Mandelbrot(double x1, double x2, double y1, double y2, int numIter,int threadNo, int NumOfThreads ) {    //Constructor
        this.realMin = x1;                      //Initial Conditions
        this.realMax = x2;
        this.complexMin = y1;
        this.complexMax = y2;
        this.iterations = numIter;
        this.heightStart = (PANEL_HEIGHT/NumOfThreads)*(threadNo-1);    //Calculating points range to process in a single Thread
        this.heightEnd = (PANEL_HEIGHT/NumOfThreads)*threadNo;
    }

    public void run(){
        double real;        //Cordinates in complex plane
        double complex;
        Complex znew;
        double unitScaleX = Math.abs( realMin - realMax )/(double)PANEL_WIDTH;          //Unit real scale
        double unitScaleY = Math.abs( complexMin - complexMax )/(double)PANEL_HEIGHT;   //Unit complex scale
        boolean divergeState;
    
        for (int j=heightStart; j<heightEnd; j++){      //Looping through Vertical axis of panel       
            for (int i=0; i<PANEL_WIDTH; i++){          //Looping through Horizontal  axis of panel

                real = realMin + i*unitScaleX;          //real part in complex plane
                complex = complexMax - j*unitScaleY;    //Complex part in complex plane
                Complex z = new Complex(0,0);           //Initial complex point
                Complex c = new Complex(real,complex);  //Maped complex point of Panel point
                divergeState = false;

                for (int s=0; s<iterations; s++){       //Count number of iteration
                    znew = Complex.square(z);           //Square of Zn
                    z = Complex.addition(c,znew);       // Zn^2 + c
                            
                    if(Complex.absolute(z)>4){          //Check for mandelbrot number
                        plotNotInSet( i, j, s );        //Not in Mandelbrot set
                        divergeState = true;
                        break;
                    }
                }
                if(divergeState == false){            //In Mandelbrot set
                    plotInSet( i, j );
                }
            }
        }
    }
}
