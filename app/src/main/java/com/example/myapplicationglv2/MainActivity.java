package com.example.myapplicationglv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import com.example.myapplicationglv2.adapters.LifeAdapter;
import com.example.myapplicationglv2.databinding.ActivityMainBinding;
import com.example.myapplicationglv2.models.Life;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private MyGLRenderer mRenderer;
    private ActivityMainBinding binding;

    private float x;
    private float y;
    private float mPreviousX;
    private float mPreviousY;
    float rot= 0.0f;
    float radio_direccion=1.8f;
    float radio_front=3.2f;
    float modulo=0.0f;
    float unitarioY,unitarioX;
    int balas = 5;

    //adapter for lifes
    private LifeAdapter adapter;
    private ArrayList<Life> lifeList = new ArrayList<>();
    private int countLifes = 3; //vidas en el juego
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setAdapter();

        binding.myGLSurfaceView.setVisibility(View.VISIBLE);
        initGLSurfaceView();
        setListeners();
    }

    private void setAdapter() {
        //inicializamos las vidas del jugador
        for (int i = 0; i < countLifes; ++i) {
            lifeList.add(new Life(true));
        }


        adapter = new LifeAdapter(lifeList);
        binding.rvLifes.setAdapter(adapter);


        //adapter.removeLife();

    }

    public void initGLSurfaceView(){
        binding.myGLSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();
        binding.myGLSurfaceView.setRenderer(mRenderer);
        binding.myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    public void setListeners(){
        //panel left
        binding.btnLeft.setOnClickListener(v->{
            /**
             * utiliza los metodos de myglrenderer y guarda las posiciones en x, y declarado arriba xd
             * */
            modulo= (float) Math.sqrt(Math.pow((mRenderer.getcenterx()-mRenderer.getfront1()),2)+Math.pow((mRenderer.getcentery()-mRenderer.getfront2()),2));
            unitarioY=-(mRenderer.getcentery()-mRenderer.getfront2())/modulo;
            unitarioX=(mRenderer.getcenterx()-mRenderer.getfront1())/modulo;
            mRenderer.setfront1(mRenderer.getfront1() + 0.1f*unitarioY);
            mRenderer.setfront2(mRenderer.getfront2() + 0.1f*unitarioX);
            mRenderer.setCenterx(mRenderer.getcenterx() + 0.1f*unitarioY);
            mRenderer.setCentery(mRenderer.getcentery() + 0.1f*unitarioX);
            mRenderer.setjugador1x(mRenderer.getjugador1x() + 0.1f*unitarioY);
            mRenderer.setjugador1y(mRenderer.getjugador1y() + 0.1f*unitarioX);
            binding.myGLSurfaceView.requestRender();
        });
        binding.btnRight.setOnClickListener(v->{
            modulo= (float) Math.sqrt(Math.pow((mRenderer.getcenterx()-mRenderer.getfront1()),2)+Math.pow((mRenderer.getcentery()-mRenderer.getfront2()),2));
            unitarioY=-(mRenderer.getcentery()-mRenderer.getfront2())/modulo;
            unitarioX=(mRenderer.getcenterx()-mRenderer.getfront1())/modulo;
            mRenderer.setfront1(mRenderer.getfront1() - 0.1f*unitarioY);
            mRenderer.setfront2(mRenderer.getfront2() - 0.1f*unitarioX);
            mRenderer.setCenterx(mRenderer.getcenterx() - 0.1f*unitarioY);
            mRenderer.setCentery(mRenderer.getcentery() - 0.1f*unitarioX);
            mRenderer.setjugador1x(mRenderer.getjugador1x() - 0.1f*unitarioY);
            mRenderer.setjugador1y(mRenderer.getjugador1y() - 0.1f*unitarioX);
            binding.myGLSurfaceView.requestRender();
        });
        binding.btnUp.setOnClickListener(v->{

            modulo= (float) Math.sqrt(Math.pow((mRenderer.getcenterx()-mRenderer.getfront1()),2)+Math.pow((mRenderer.getcentery()-mRenderer.getfront2()),2));
            unitarioY=(mRenderer.getcentery()-mRenderer.getfront2())/modulo;
            unitarioX=(mRenderer.getcenterx()-mRenderer.getfront1())/modulo;
            mRenderer.setfront1(mRenderer.getfront1() + 0.1f*unitarioX);
            mRenderer.setfront2(mRenderer.getfront2() + 0.1f*unitarioY);
            mRenderer.setCenterx(mRenderer.getcenterx() + 0.1f*unitarioX);
            mRenderer.setCentery(mRenderer.getcentery() + 0.1f*unitarioY);
            mRenderer.setjugador1x(mRenderer.getjugador1x() + 0.1f*unitarioX);
            mRenderer.setjugador1y(mRenderer.getjugador1y() + 0.1f*unitarioY);
            binding.myGLSurfaceView.requestRender();
        });
        binding.btnDown.setOnClickListener(v->{
            modulo= (float) Math.sqrt(Math.pow((mRenderer.getcenterx()-mRenderer.getfront1()),2)+Math.pow((mRenderer.getcentery()-mRenderer.getfront2()),2));
            unitarioY=(mRenderer.getcentery()-mRenderer.getfront2())/modulo;
            unitarioX=(mRenderer.getcenterx()-mRenderer.getfront1())/modulo;
            mRenderer.setfront1(mRenderer.getfront1() - 0.1f*unitarioX);
            mRenderer.setfront2(mRenderer.getfront2() - 0.1f*unitarioY);
            mRenderer.setCenterx(mRenderer.getcenterx() - 0.1f*unitarioX);
            mRenderer.setCentery(mRenderer.getcentery() - 0.1f*unitarioY);
            mRenderer.setjugador1x(mRenderer.getjugador1x() - 0.1f*unitarioX);
            mRenderer.setjugador1y(mRenderer.getjugador1y() - 0.1f*unitarioY);
            binding.myGLSurfaceView.requestRender();
        });

        //panel right
        binding.btnFront.setOnClickListener(v->{

            //mRenderer.setfront3(mRenderer.getfront3() + 0.1f);
            //mRenderer.setCenterz(mRenderer.getcenterz() + 0.1f);
            rot=rot+0.05f;

            mRenderer.setfront1((float) (-0.0f+mRenderer.getjugador1x() + Math.sin(rot+Math.PI)*radio_front));
            mRenderer.setfront2((float) (-3.0f+mRenderer.getjugador1y() + Math.cos(rot+Math.PI)*radio_front));

            mRenderer.setCenterx((float) (-0.0f+mRenderer.getjugador1x() + Math.sin(rot)*radio_direccion));
            mRenderer.setCentery((float) (-3.0+mRenderer.getjugador1y() + Math.cos(rot)*radio_direccion));

            binding.myGLSurfaceView.requestRender();
        });
        binding.btnAfter.setOnClickListener(v->{

            rot=rot-0.05f;

            mRenderer.setfront1((float) (-0.0f+mRenderer.getjugador1x() + Math.sin(rot+Math.PI)*radio_front));
            mRenderer.setfront2((float) (-3.0f+mRenderer.getjugador1y() + Math.cos(rot+Math.PI)*radio_front));

            mRenderer.setCenterx((float) (-0.0f+mRenderer.getjugador1x() + Math.sin(rot)*radio_direccion));
            mRenderer.setCentery((float) (-3.0+mRenderer.getjugador1y() + Math.cos(rot)*radio_direccion));

            binding.myGLSurfaceView.requestRender();
        });

        binding.btnA.setOnClickListener(v->{
            modulo= (float) Math.sqrt(Math.pow((mRenderer.getcenterx()-mRenderer.getfront1()),2)+Math.pow((mRenderer.getcentery()-mRenderer.getfront2()),2));
            unitarioY=(mRenderer.getcentery()-mRenderer.getfront2())/modulo;
            unitarioX=(mRenderer.getcenterx()-mRenderer.getfront1())/modulo;
            mRenderer.setlaser1x(mRenderer.getjugador1x());
            mRenderer.setlaser1y(mRenderer.getjugador1y());
            if(balas>0) {
                for (int i = 0; i < 200000; i++) {
                    mRenderer.setlaser1x(mRenderer.getlaser1x() + 0.00002f * unitarioX);
                    mRenderer.setlaser1y(mRenderer.getlaser1y() + 0.00002f * unitarioY);
                    binding.myGLSurfaceView.requestRender();
                }
                balas--;
            }
            else Toast.makeText(this,"Recargar!",Toast.LENGTH_LONG).show();
            mRenderer.setlaser1x(100.0f); //Numero arbitrario lejos del mapa


        });
        binding.btnB.setOnClickListener(v->{
            balas=5;
            Toast.makeText(this,"5 balas",Toast.LENGTH_LONG).show();
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.myGLSurfaceView.onPause();
    }
    @Override
    protected void onResume() {

        super.onResume();
        binding.myGLSurfaceView.onResume();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); //anula el onbackpress en la actividad
    }
}