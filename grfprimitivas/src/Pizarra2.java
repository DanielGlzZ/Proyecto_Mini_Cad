/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gmendez
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Pizarra2 extends javax.swing.JFrame {
    /**
     * Creates new form Pizarra2
     */

    static final int LINEA     = 0;
    static final int TRIANGULO = 1;
    static final int CUADRILATERO  = 2;
    static final int POLIGONO  = 3;
    static final int CIRCULO   = 4;
    static final int RECTANGULO= 5;
    static final int OVALO     = 6;
    static final int MOVER     = 100;
    
    static final double NUMTRIAN  = 50.00;
    
    static final int ANCHO     = 640;
    static final int ALTO      = 480;
    
    Raster  raster;    
    
    Point   p1, p2, p3, p4;
    boolean bP1=false, bP2=false, bP3=false, bP4=false, bP5=false;
    int     figura = LINEA; 
    int     contador = 1;
    int     figSelect;
         
        
    JPanel      panelRaster;
    JPanel      panelControles;
    JPanel      panelFiguras;
    JScrollPane scrollFiguras;
    
    JList             listFiguras;
    DefaultListModel  listModel;    
    ArrayList<Figura> aFiguras;
    
    JButton       btnColor;
    JToggleButton rbLinea;
    JToggleButton rbTriang;
    JToggleButton rbCuadrado;
    JToggleButton rbPoligono;
    JToggleButton rbCirculo;
    JToggleButton rbRectangulo;
    JToggleButton rbOvalo;
    //JToggleButton       mover;

    ButtonGroup   bg;
    
    Color         color;
    JColorChooser colorChooser;
    //JButton       btnGuardarRast;
    //JButton       btnGuardarVect;
    //JButton       abrir;
    //JButton       reset;
    JButton       tras;
    
    JTextField    selec;
    JTextField    tX;
    JTextField    tY;
    JTextField    ro; 
    JTextField    es;
    public Pizarra2() {      
        p1 = new Point();
        p2 = new Point();        
        p3 = new Point();
        
        bP1 = false; bP2 = false; bP3 = false;
        
        raster = new Raster(ANCHO,ALTO);        
        
        panelRaster = new MyPanel(raster);
        
        panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles,BoxLayout.Y_AXIS));
                
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.setLayout(new BorderLayout());       
        
        color = Color.black;
        
        //btnGuardarRast = new JButton("Guardar img");   
        btnColor       = new JButton("Color");
        
        btnColor.setBorderPainted(false);
        btnColor.setFocusPainted(false);
                
        btnColor.setBackground(color);
        btnColor.setForeground(color);
        
        rbLinea  = new JToggleButton("Linea");
        rbTriang = new JToggleButton("Triangulo");
        rbCuadrado = new JToggleButton("Cuadrado");
        rbPoligono = new JToggleButton("Polígono");
        rbCirculo  = new JToggleButton("Círculo");
        rbRectangulo = new JToggleButton("Rectángulo");
        rbOvalo = new JToggleButton("Óvalo");
        //mover = new JToggleButton("Mover");
        
        tX = new JTextField();
        tX.setMaximumSize(new Dimension(100,10)); 
        tY = new JTextField();
        tY.setMaximumSize(new Dimension(100,10)); 
        ro = new JTextField();
        ro.setMaximumSize(new Dimension(100,10)); 
        es = new JTextField();
        es.setMaximumSize(new Dimension(100,10));
        tras  = new JButton("Trasladar");
    
        bg = new ButtonGroup();
        
        rbLinea.setSelected(true);
        bg.add(rbLinea);
        bg.add(rbTriang);
        bg.add(rbCuadrado);
        bg.add(rbPoligono);
        bg.add(rbCirculo);
        bg.add(rbRectangulo);
        bg.add(rbOvalo);
        //bg.add(mover);
       
        this.panelRaster.setBackground(Color.white);
        this.add(panelRaster,BorderLayout.CENTER);

        this.panelControles.add(rbLinea);
        this.panelControles.add(rbTriang);
        this.panelControles.add(rbCuadrado);
        this.panelControles.add(rbPoligono);
        this.panelControles.add(rbCirculo);
        this.panelControles.add(rbRectangulo);
        this.panelControles.add(rbOvalo);
        //this.panelControles.add(mover);
        this.panelControles.add(new JSeparator());
        this.panelControles.add(tX);
        this.panelControles.add(tY);
        this.panelControles.add(tras);
        this.panelControles.add(ro);
        this.panelControles.add(es);
        this.panelControles.add(new JSeparator());
        this.panelControles.add(btnColor);

        //this.panelControles.add(btnGuardarRast);
        
        // Ahora el pane de figuras
        
        //btnGuardarVect = new JButton("Guardar txt");
        //reset = new JButton("Reset");
        //abrir = new JButton("abrir");
        selec = new JTextField();
        selec.setMaximumSize(new Dimension(100,10)); 
        
        scrollFiguras = new JScrollPane();
        panelFiguras  = new JPanel();
        
        panelFiguras.setLayout(new BoxLayout(panelFiguras,BoxLayout.Y_AXIS));       
                        
        listFiguras = new JList(); 
        listFiguras.setModel(new DefaultListModel());
        listModel = (DefaultListModel) listFiguras.getModel();
        
        scrollFiguras.setViewportView(listFiguras);
                
        scrollFiguras.setPreferredSize(new Dimension(50,100));
        panelFiguras.add(scrollFiguras);
        panelFiguras.add(new JSeparator());
        //panelFiguras.add(btnGuardarVect);
        //panelFiguras.add(reset);
        //panelFiguras.add(abrir);
        panelFiguras.add(selec);
        
        aFiguras = new ArrayList<Figura>();        
                
        this.add(panelFiguras,BorderLayout.EAST);
        this.add(panelControles,BorderLayout.WEST);

        this.panelRaster.addMouseListener(new MouseAdapter(){
                                        @Override
                                        public void mouseClicked(MouseEvent evt) {
                                                jPanel1MouseClicked(evt);
                                        }                          
                                        @Override
                                        public void mouseDragged(MouseEvent evt){
                                                jPanelMouseDragged(evt);
                                        }
                                }); 
        
        
        this.panelRaster.addKeyListener(new KeyAdapter(){
                                       @Override
                                       public void keyReleased(KeyEvent ke){
                                             
                                                 jPanel1KeyReleased(ke);
                                             
                                       }
                                 });        
        
        this.btnColor.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                        color = JColorChooser.showDialog(null,"Seleccione un color",color);
                                                        btnColor.setBackground(color);
                                                }
                                });

        /*this.btnGuardarRast.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                        guardarImagen();
                                                }
                                });
        */
        /*this.btnGuardarVect.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                        guardarVectores();
                                                }
                                });    
        */
       /* this.reset.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                          bP1=false;
                                          bP2=false;
                                          bP3=false;
                                          bP4=false;
                                          bP5=false;
//                                          listFiguras.removeAll();
//                                          clear();
//                                          listModel.clear();
                                        }});
        */
        /*this.abrir.addActionListener(new ActionListener(){
                                         @Override
                                         public void actionPerformed(ActionEvent e){
                                           leerTxt();
                                         }});
        */
        this.selec.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
            int a=0;
            try{
              a = Integer.parseInt(selec.getText());
            } catch (NumberFormatException ex) {
              System.out.println("Solo puedes introducir números enteros");
              figSelect = 0;
            }
            if(a>0 && a< contador){
              figSelect = a;
              System.out.println("Figura "+a);
            } else {
              System.out.println("ID de Figura inválida");
              figSelect = 0;
            }
          }          
        });
        
        this.tras.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
            int x=0,y=0;
            try{
              x = Integer.parseInt(tX.getText());
              y = Integer.parseInt(tY.getText());
            } catch (NumberFormatException ex) {
              System.out.println("Solo puedes introducir números enteros");
              x=0;
              y=0;
            }
            if(figSelect > 0){
              for(Figura figura:aFiguras){
                if(figura.index == figSelect){
                  if (figura instanceof TrianguloR){
                    for (int i=0; i <3; i++){
                      ((TrianguloR) figura).getV()[i].x += x;
                      ((TrianguloR) figura).getV()[i].y += y;
                    }                 
                  } else if (figura instanceof Linea){
                    ((Linea) figura).punto1.x += x;
                    ((Linea) figura).punto1.y += y;
                    ((Linea) figura).punto2.x += x;
                    ((Linea) figura).punto2.y += y;
                  }                                    
                }                                
              }
              raster.fill(Color.white);
              for(Figura figura:aFiguras){
                if(figura instanceof TrianguloR){
                  ((TrianguloR) figura).dibujar(raster);
                } else if (figura instanceof Linea){
                  dibujarLinea(((Linea) figura).punto1, ((Linea) figura).punto2, ((Linea) figura).color);
                }
              }
              panelRaster.repaint();
            }
          }          
        });
        
        this.ro.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e){
            double ang=0;
            try{
              ang = Integer.parseInt(ro.getText());
            } catch (NumberFormatException ex) {
              System.out.println("Solo puedes introducir números enteros");
              ang = 0;
            }
            ang = Math.toRadians(ang);
            if(figSelect > 0){
              for(Figura figura: aFiguras){
                if(figura.index == figSelect){
                  if (figura instanceof TrianguloR){
                    int x, y,x2,y2;
                    for (int i=0; i <2; i++){
                      x = ((TrianguloR) figura).v[i+1].x - ((TrianguloR) figura).v[0].x;
                      y = ((TrianguloR) figura).v[i+1].y - ((TrianguloR) figura).v[0].y;
                      x2 = (int)(x * Math.cos(ang) - y * Math.sin(ang));
                      y2 = (int)(x * Math.sin(ang) + y * Math.cos(ang));
                      
                      ((TrianguloR) figura).v[i+1].x = x2 + ((TrianguloR) figura).v[0].x;
                      ((TrianguloR) figura).v[i+1].y = y2 + ((TrianguloR) figura).v[0].y;
                    }
                  }
                }
              }
              raster.fill(Color.white);
              for(Figura figura:aFiguras){
                if(figura instanceof TrianguloR){
                  ((TrianguloR) figura).dibujar(raster);
                } else if (figura instanceof Linea){
                  dibujarLinea(((Linea) figura).punto1, ((Linea) figura).punto2, ((Linea) figura).color);
                }
              }
              panelRaster.repaint();
            }
          }
        });
        
        this.es.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e) {
            double esc=0;
            try{
              esc = Double.parseDouble(es.getText());
            } catch (NumberFormatException ex) {
              System.out.println("Solo puedes introducir números enteros");
              esc = 0;
            }
            if(figSelect > 0){
              for(Figura figura: aFiguras){
                if(figura.index == figSelect){
                  if (figura instanceof TrianguloR){
                    int xAnt, yAnt;
                    xAnt =((TrianguloR) figura).getV()[0].x;
                    yAnt =((TrianguloR) figura).getV()[0].y;
                    for (int i=0; i <3; i++){
                    ((TrianguloR) figura).getV()[i].x = (int) (esc*(((TrianguloR) figura).getV()[i].x));
                    ((TrianguloR) figura).getV()[i].y = (int) (esc*(((TrianguloR) figura).getV()[i].y));                     
                    }
                    int xNue, yNue;
                    xNue =((TrianguloR) figura).getV()[0].x;
                    yNue =((TrianguloR) figura).getV()[0].y;
                    int dX, dY;
                    dX = xNue - xAnt;
                    dY = yNue - yAnt;
                    for (int i=0; i <3; i++){
                      ((TrianguloR) figura).getV()[i].x -= dX;
                      ((TrianguloR) figura).getV()[i].y -= dY;
                    }                    
                  } else if (figura instanceof Linea){
                    ((Linea) figura).punto1.x *= esc;
                    ((Linea) figura).punto1.y *= esc;
                    ((Linea) figura).punto2.x *= esc;
                    ((Linea) figura).punto2.y *= esc;
                  }
                }
              }
              raster.fill(Color.white);
              for(Figura figura:aFiguras){
                if(figura instanceof TrianguloR){
                  ((TrianguloR) figura).dibujar(raster);
                } else if (figura instanceof Linea){
                  dibujarLinea(((Linea) figura).punto1, ((Linea) figura).punto2, ((Linea) figura).color);
                }
              }
              panelRaster.repaint();
            }
          }
          
        });
        
        
        this.setVisible(true);
        this.pack();
        
    }
    
    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }    
    
    public void guardarImagen(){
        
        BufferedImage img = toBufferedImage(raster.toImage(this));        
        try {                        
            File outputfile = new File("saved.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
          
        }                
    }
    
    public void guardarVectores() {        
        FileWriter fw = null;
        String linea="";

        try {
            fw = new FileWriter("vectores.txt");
            for (int i=0;i<aFiguras.size();i++){
                Figura f = aFiguras.get(i);
                
                if (f instanceof Linea){
                    Linea l = (Linea)f;
                   
                    linea=String.format("L,%.0f,%.0f,%.0f,%.0f,%x\n",l.punto1.getX(),l.punto1.getY(),
                                                          l.punto2.getX(),l.punto2.getY(),
                                                          l.color.getRGB());                                        
                } 
                
                if (f instanceof TrianguloR){
                    TrianguloR t = (TrianguloR)f;                   
                    linea=String.format("T,%d,%d,%d,%d,%d,%d,%x\n",t.v[0].x,t.v[0].y,
                                                                  t.v[1].x,t.v[1].y,
                                                                  t.v[2].x,t.v[2].y,
                                                                  t.color.getRGB());                       
                }
                
                fw.write(linea);
            }
        } catch (IOException ex) {
            Logger.getLogger(Pizarra2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Pizarra2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public void leerTxt(){
      FileDialog seleccion = new FileDialog(this,"Abrir Vectores",FileDialog.LOAD);
      seleccion.setFilenameFilter(new FilenameFilter() {
               @Override
               public boolean accept(File dir, String name) {
                 return name.endsWith(".txt");
          }
      });
      seleccion.setVisible(true);

        if (seleccion != null) {
          String[] info;
          File archivo = new File("C:\\Users\\Andre\\Desktop\\5to-semestre\\graficación\\grfprimitivas\\vectores.txt");
          ArrayList<String> listaFiguras = Archivo.leerArchivo(archivo);
          for(String linea:listaFiguras){
            info=linea.split(",");
            switch(info[0]){
              case "T":
                p1.x = Integer.parseInt(info[1]);
                p1.y = Integer.parseInt(info[2]);
                p2.x = Integer.parseInt(info[3]);
                p2.y = Integer.parseInt(info[4]);
                p3.x = Integer.parseInt(info[5]);
                p3.y = Integer.parseInt(info[6]);
                color = hex2Rgb(info[7]);

                dibujarTriangulo(p1,p2,p3,color);
               break;
            
              case "L":
                p1.x = Integer.parseInt(info[1]);
                p1.y = Integer.parseInt(info[2]);
                p2.x = Integer.parseInt(info[3]);
                p2.y = Integer.parseInt(info[4]);
                color = hex2Rgb(info[5]);
                
                dibujarLinea(p1, p2, color);
                break;
            }
            panelRaster.repaint();
          }
          
        }
    }
           
    public void clear() {	
        int s = raster.size();
        for (int i = 0; i < s; i++) {
            raster.pixel[i] ^= 0x00ffffff;
        }
        repaint();
        return;
    }    
    
    public void lineaSimple(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dx = x1 - x0;
        int dy = y1 - y0;

        raster.setPixel(pix, x0, y0);

        if (dx != 0) {
            float m = (float) dy / (float) dx;
            float b = y0 - m*x0;

            dx = (x1 > x0) ? 1 : -1;

            while (x0 != x1) {
                x0 += dx;
                y0 = Math.round(m*x0 + b);
                raster.setPixel(pix, x0, y0);
            }
        }
    }
    
    public void lineaMejorada(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dx = x1 - x0;  int dy = y1 - y0;
        raster.setPixel(pix, x0, y0);
        if (Math.abs(dx) > Math.abs(dy)) {     // inclinacion < 1
            float m = (float) dy / (float) dx; // calcular inclinacion
            float b = y0 - m*x0;
            dx = (dx < 0) ? -1 : 1;
            while (x0 != x1) {
                x0 += dx;
                raster.setPixel(pix, x0, Math.round(m*x0 + b));
            }
        } else {
            if (dy != 0) {                         // inclinacion >= 1
                float m = (float) dx / (float) dy; // Calcular inclinacion
                float b = x0 - m*y0;
                dy = (dy < 0) ? -1 : 1;
                while (y0 != y1) {
                    y0 += dy;
                    raster.setPixel(pix, Math.round(m*y0 + b), y0);
                }
            }
        }
    }

    public void lineFast(int x0, int y0, int x1, int y1, Color color) {
        int pix = color.getRGB();
        int dy = y1 - y0;  int dx = x1 - x0;  int stepx, stepy;
        if (dy < 0) { dy = -dy;  stepy = -raster.width; } else { stepy = raster.width; }
        if (dx < 0) { dx = -dx;  stepx = -1; } else { stepx = 1; }
        dy <<= 1;   dx <<= 1;
        y0 *= raster.width;  y1 *= raster.width;   raster.pixel[x0+y0] = pix;
        if (dx > dy) {
            int fraction = dy - (dx >> 1);
            while (x0 != x1) {
                if (fraction >= 0) {
                    y0 += stepy; fraction -= dx;
                }
                x0 += stepx;  fraction += dy;
                raster.pixel[x0+y0] = pix;
            }
        } else {
            int fraction = dx - (dy >> 1);
            while (y0 != y1) {
                if (fraction >= 0) {
                    x0 += stepx; fraction -= dy;
                }
                y0 += stepy; fraction += dx;
                raster.pixel[x0+y0] = pix;
            }
        }
    }
    
    private void dibujarLinea(Point _p1, Point _p2, Color color) {
             long inicio=0, fin=0;
             //inicio = System.nanoTime();
             // lineaMejorada(_p1.x,_p1.y,_p2.x,_p2.y,color);
             //fin    = System.nanoTime();
             
             //System.out.printf("Tiempo transcurrido, simple: %d\n",(fin-inicio));
             
             //inicio = System.nanoTime();
             lineFast(_p1.x,_p1.y,_p2.x,_p2.y,color);
             //fin    = System.nanoTime();            
             
             //System.out.printf("Tiempo transcurrido, fast  : %d\n",(fin-inicio));             
    }    
    
    
    private void dibujarTriangulo(Point p1, Point p2, Point p3, Color c){
                // TODO add your handling code here:
        Vertex2D v1 = new Vertex2D(p1.x,p1.y,c.getRGB());
        Vertex2D v2 = new Vertex2D(p2.x,p2.y,c.getRGB());
        Vertex2D v3 = new Vertex2D(p3.x,p3.y,c.getRGB());
        
        TrianguloR tri = new TrianguloR(v1,v2,v3,c, contador);

        tri.dibujar(raster);
        
        aFiguras.add(tri);
    }
    
    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        //switch? no.
        if(rbLinea.isSelected()){
          figura = LINEA;
        } else if(rbTriang.isSelected()){
          figura = TRIANGULO;
        } else if (rbCuadrado.isSelected()){
          figura = CUADRILATERO;
        } else if (rbPoligono.isSelected()){
          figura = POLIGONO;
        } else if (rbCirculo.isSelected()){
          figura = CIRCULO;
        } else if (rbRectangulo.isSelected()){
          figura = RECTANGULO;
        } else if (rbOvalo.isSelected()){
          figura = OVALO;
        } /*else if (mover.isSelected()){
          figura = MOVER;
          panelRaster.requestFocus();
        }
        
        if(figura==MOVER && figSelect != 0){
          /*deltaX = e.getX() - circuloSeleccionado.x;
            deltaY = e.getY() - circuloSeleccionado.y;
          */ /*
          System.out.println("mover");
        }
       */
        if(figura!=POLIGONO && bP5){      
          listModel.addElement("Polígono,   "+contador);
          contador++;
          bP1=false;bP2=false;bP3=false;bP4=false;            
          bP5=false;
        }
        
        if(bP5){
          p3=evt.getPoint();
        }
        
        if (bP1 && bP2 && bP3 && !bP4){
          p4 = evt.getPoint();
          bP4=true;
          System.out.println("Cuarto Punto");
        }
        
        if ((figura != LINEA) && bP1 && bP2 && !bP3){
            p3.x=evt.getX();
            p3.y=evt.getY();
            bP3 = true;              
            System.out.println("Tercer punto");
            
        } 

        if (bP1 && !bP2){
            p2.x=evt.getX();
            p2.y=evt.getY();
            bP2 = true;                       
            
            dibujarLinea(p2,p2,color);
            
            if(figura == LINEA){
                
                Linea l = new Linea(p1,p2, color, contador); 
                
                String linea=String.format(contador+",   "+"L,%.0f,%.0f,%.0f,%.0f,%x\n",l.punto1.getX(),l.punto1.getY(),
                                                          l.punto2.getX(),l.punto2.getY(),
                                                          l.color.getRGB());
                    
                
                aFiguras.add(l);
                listModel.addElement(linea);   
                contador++;
            }    
        } 
        
        if (!bP1){
            p1.x=evt.getX();
            p1.y=evt.getY();
            bP1 = true;

            dibujarLinea(p1,p1,color);
        }         
        
        if(figura==LINEA && bP1 && bP2){
            dibujarLinea(p1,p2,color);
            panelRaster.repaint();
            bP1=false;bP2=false;bP3=false;
        }
        
        if(figura==TRIANGULO && bP1 &&bP2 &&bP3){
            System.out.println("Dibujando triangulo");
            dibujarTriangulo(p1,p2,p3,color);
            panelRaster.repaint();
            listModel.addElement("Triangulo,  "+contador);
            contador++;
            bP1=false;bP2=false;bP3=false;
        }     
        
        if ((figura==POLIGONO || figura==CUADRILATERO) && bP1 && bP2 && bP3 && bP4 && !bP5){
          dibujarTriangulo(p1,p2,p3,color);
          dibujarTriangulo(p1,p3,p4,color);
          panelRaster.repaint();
          if(figura==CUADRILATERO){            
          System.out.println("Dibujando Cuadrado");
          listModel.addElement("Cuadrilatero,  "+contador);
          contador++;
          bP1=false;bP2=false;bP3=false;bP4=false;
          }
          bP5=true;
        } else if (figura==POLIGONO && bP5){
          dibujarTriangulo(p1,p3,p4,color);          
          p4=p3;
          System.out.println("Dibujando nuevo subtriángulo");
          panelRaster.repaint();
        }
        
        if (figura==RECTANGULO && bP1 && bP2){
          p3.x = p1.x;
          p3.y = p2.y;
          dibujarTriangulo(p1,p2,p3,color);
          p3.x = p2.x;
          p3.y = p1.y;
          dibujarTriangulo(p1,p2,p3,color);
          panelRaster.repaint();
          listModel.addElement("Rectángulo,   "+contador);   
          contador++;
          bP1=false; bP2=false;
        }
        
        if (figura==OVALO && bP1 && bP2){
          double angulo = 360.00 / NUMTRIAN;
          double a = p2.x - p1.x;
          double b = p2.y - p1.y;
          //si se quiere hacer un óvalo dentro de un rectángulo se usa esto:
//              a=a/2;
//              b=b/2;
          p1.x = p2.x - (int)a;
          p1.y = p2.y - (int)b;
          p2.y = p2.y-(int)b;
          for(int i = 1; i < NUMTRIAN+1;i++){
          p3.x = (int)(p1.x+a*Math.cos(angulo*i*Math.PI/180));
          p3.y = (int)(p1.y+b*Math.sin(angulo*i*Math.PI/180));

          dibujarTriangulo(p1,p2,p3,color);

          p2.x = p3.x;
          p2.y = p3.y;
          }
          panelRaster.repaint();
          listModel.addElement("Óvalo,   "+contador);       
          contador++;
          bP1 = false; bP2 = false;
        }
        
        if(figura==CIRCULO && bP1 && bP2){
          System.out.println(bP1+"/"+bP2);
          double angulo = 360.00 / NUMTRIAN;
          double distancia;
          distancia = p1.distance(p2);
          System.out.println(distancia);
          for(int i = 1; i<=NUMTRIAN+1; i++){
  //                System.out.println(i);
  //                System.out.println(angulo*i);
            p3.x = (int) (p1.x + distancia*Math.cos(Math.toRadians(angulo*i)));
            p3.y = (int) (p1.y + distancia*Math.sin(Math.toRadians(angulo*i)));
            dibujarTriangulo(p1,p2,p3,color);
            p2.x = p3.x;
            p2.y = p3.y;
          }
          panelRaster.repaint();
          listModel.addElement("Circulo,   "+contador);   
          contador++;
          System.out.println(angulo);
          bP1 = false; bP2 = false;
          System.out.println(bP1+"/"+bP2);              
        }
    }
    
    public void jPanelMouseDragged(java.awt.event.MouseEvent evt){
    /*if (mover.isSelected()){
          figura = MOVER;
      }
      System.out.println("a");
      if(figura==MOVER && figSelect != 0){
        System.out.println("asdasd");
      //circuloSeleccionado.x = e.getX() - deltaX;
      //circuloSeleccionado.y = e.getY() - deltaY;
      
        for(Figura figura : aFiguras){
          if(figura.index == figSelect){
            if(figura instanceof Linea){
              
            } else if (figura instanceof TrianguloR){
              System.out.println("chi");
              for(int i = 0; i <3; i++){
                ((TrianguloR) figura).getV()[i].x += evt.getPoint().x;
                ((TrianguloR) figura).getV()[i].y += evt.getPoint().y;
              }
              ((TrianguloR) figura).dibujar(raster);
            }
          }
        }
      }
    */
    }
    
    public void jPanel1KeyReleased(KeyEvent ke) {      
        
        if(ke.getKeyCode()==KeyEvent.VK_T){
            this.figura = 1;
        }

        if(ke.getKeyCode()==KeyEvent.VK_L){
            this.figura = 0;
        }        
        switch(ke.getKeyCode()){
          case KeyEvent.VK_R:
            bP1 = false;
            bP2 = false;
            bP3 = false;
            bP4 = false;
            bP5 = false;
            System.out.println("reset");
            break;
          case KeyEvent.VK_ENTER:
            int a=0;
            try{
              a = Integer.parseInt(selec.getText());
            } catch (NumberFormatException ex) {
              System.out.println("Solo puedes introducir números");
            }
            if(a>0 && a< contador){
              figSelect = a;
            } else {
              System.out.println("ID de Figura inválida");
            }
            
            break;
        }

    } 
                      
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pizarra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pizarra2 pizarra = new Pizarra2();
                pizarra.setVisible(true);
                
            }
        });
    }

}
