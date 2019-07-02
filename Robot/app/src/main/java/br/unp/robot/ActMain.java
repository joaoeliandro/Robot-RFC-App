package br.unp.robot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static android.widget.SeekBar.*;

public class ActMain extends AppCompatActivity {
    TabHost th;
    TextView txtStatusConn, txtMsgServer, txtStatusCmd;
    EditText txtIpSever;
    Button btnConect, btnFrente, btnTras, btnEsquerda, btnDireita, btnParar, btnArma, btndesarma;
    RequestQueue myRequest;
    SeekBar sbspeed;
    public static String IP;

    int cont=0;

        public void habilitarTabs(){
            th = (TabHost) findViewById(R.id.tabhost);
            th.setup();

            /*Configurações da Tab 1*/
            TabHost.TabSpec ts1 = th.newTabSpec("Tabconnect");
            ts1.setIndicator("Conexão WiFi");
            ts1.setContent(R.id.tab1);

            /*Configurações da Tab 2*/
            TabHost.TabSpec ts2 = th.newTabSpec("Tabcomandos");
            ts2.setIndicator("Painel de Comandos");
            ts2.setContent(R.id.tab2);

            /*Adicionando as Tabs ao TabHost*/
            th.addTab(ts1);
            th.addTab(ts2);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.act_main);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);



                /*Link de objetos com actMain*/
                btnConect = (Button) findViewById(R.id.btnConectar);
                txtIpSever = (EditText) findViewById(R.id.txtIPHost);
                txtStatusConn = (TextView) findViewById(R.id.txtStatusConn);
                txtMsgServer = (TextView) findViewById(R.id.txtMsgServer);
                txtStatusCmd = (TextView) findViewById(R.id.txtCmd);

                btnFrente = findViewById(R.id.btnFrente);
                btnTras = findViewById(R.id.btnTras);
                btnEsquerda = findViewById(R.id.btnEsquerda);
                btnDireita = findViewById(R.id.btnDireita);
                btnParar = findViewById(R.id.btnParar);
                btnArma = findViewById(R.id.btnArma);
                btndesarma = findViewById(R.id.btnDesarma);
                sbspeed = findViewById(R.id.sbSpeed);

                habilitarTabs();
                th.getTabWidget().getChildTabViewAt(1).setEnabled(true);

            sbspeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int vel = sbspeed.getProgress();

                    switch (vel) {
                        case 0:
                            speed0();
                            break;
                        case 1:
                            speed170();
                            break;
                        case 2:
                            speed200();
                            break;
                        case 3:
                            speed255();
                            break;

                    }
                }
            });


                   /*Configurar Listener dos botões*/
                    btnConect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if("Conectar".equals(btnConect.getText())){
                                conectar();
                                }else if("Desconectar".equals(btnConect.getText())){
                                 desconectar();
                                }
                        }
                        });
                    btnFrente.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            frente();
                        }
                    });
                    btnTras.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tras();
                        }
                    });

                    btnEsquerda.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            esquerda();
                        }
                    });
                    btnDireita.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            direita();
                        }
                    });
                    btnArma.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            arma();
                        }
                    });

                    btnParar.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            parar();
                        }
                    });
                    btndesarma.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            desarma();
                        }
                    });


                    myRequest = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

               /* Toast msg = Toast.makeText(getApplicationContext(), "Actmain criado", Toast.LENGTH_SHORT);
                msg.show();*/
        }

        public void speed0(){
        String url = "http://"+IP+"/0";

        //RequestQueue   myRequest = Volley.newRequestQueue(this);

        final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtStatusCmd.setText(response);
                cont=0;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (cont > 0) {
                    txtStatusCmd.setText("Erro: " + error);
                    cont=0;
                } else try {
                    Thread.sleep(500);
                    cont++;
                   // speed0();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    txtMsgServer.setText("Erro: " + e);
                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(strSpeed);

    }

        public void speed170(){
        String url = "http://"+IP+"/1";

        //RequestQueue myRequest = Volley.newRequestQueue(this);

        final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtStatusCmd.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtStatusCmd.setText("Erro: "+error);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(strSpeed);

    }

        public void speed200(){
        String url = "http://"+IP+"/2";

        //RequestQueue  myRequest = Volley.newRequestQueue(this);

        final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtStatusCmd.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtStatusCmd.setText("Erro: "+error);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(strSpeed);

    }

        public void speed255(){
        String url = "http://"+IP+"/3";

        // RequestQueue   myRequest = Volley.newRequestQueue(this);

        final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtStatusCmd.setText(response);
                cont=0;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (cont > 0) {
                    txtStatusCmd.setText("Erro: " + error);
                    cont=0;
                } else try {
                    Thread.sleep(500);
                    cont++;
                   // speed255();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    txtMsgServer.setText("Erro: " + e);
                }
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(strSpeed);

    }

        public void conectar(){
                IP = txtIpSever.getText().toString();
                String url = "http://"+IP+"/c";

                if ("".equals(IP)) {
                    return;
                }
                //RequestQueue myRequest = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
                //myRequest = Volley.newRequestQueue(ActMain.this);
                final StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                txtStatusConn.setText("OnLine!");
                                txtStatusConn.setTextColor(getResources().getColor(R.color.verde));
                                txtMsgServer.setText(response);
                                btnConect.setText("Desconectar");
                                cont=0;
                                th.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                                th.getTabWidget().getChildTabViewAt(1).setFocusable(true);
                               }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (cont>0){
                        txtStatusConn.setText("Off-Line!");
                        txtStatusConn.setTextColor(getResources().getColor(R.color.vermelho));
                        txtMsgServer.setText("Erro: "+error+", "+IP);
                        btnConect.setText("Conectar");
                        cont=0;
                        }else{
                            try {
                                Thread.sleep(500);
                                cont++;
                               // conectar();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                txtMsgServer.setText("Erro: "+e);
                            }

                        }

                    }
                });

                MySingleton.getInstance(this).addToRequestQueue(strRequest);

            }

        public void desconectar(){
            myRequest.stop();

            btnConect.setText("Conectar");
            txtStatusConn.setText("Off-Line");
            txtStatusConn.setTextColor(getResources().getColor(R.color.vermelho));
            txtMsgServer.setText("...");
            cont=0;
        }

        public void parar(){
            String url = "http://"+IP+"/p";

            //RequestQueue myRequest = Volley.newRequestQueue(this);
            final StringRequest strParar = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    txtStatusCmd.setText(response);
                    cont=0;

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (cont > 0) {
                        txtStatusCmd.setText("Erro: " + error);
                        cont=0;
                    } else try {
                        Thread.sleep(500);
                        cont++;
                      //  parar();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        txtMsgServer.setText("Erro: " + e);
                    }
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strParar);

        }

        public void arma(){
            String url = "http://"+IP+"/w";

            //RequestQueue    myRequest = Volley.newRequestQueue(this);

            final StringRequest strMover = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    txtStatusCmd.setText(response);
                     cont=0;

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (cont > 0) {
                        txtStatusCmd.setText("Erro: " + error);
                        cont=0;
                    } else try {
                        Thread.sleep(500);
                        cont++;
                      //  arma();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        txtMsgServer.setText("Erro: " + e);
                    }

                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strMover);

        }

        public void desarma(){
        String url = "http://"+IP+"/d";

        //RequestQueue    myRequest = Volley.newRequestQueue(this);

        final StringRequest strMover = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                txtStatusCmd.setText(response);
                cont=0;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (cont > 0) {
                    txtStatusCmd.setText("Erro: " + error);
                    cont=0;
                } else try {
                    Thread.sleep(500);
                    cont++;
                  //  desarma();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    txtMsgServer.setText("Erro: " + e);
                }

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(strMover);

    }

        public void frente(){
            String url = "http://"+IP+"/u";

            //RequestQueue myRequest = Volley.newRequestQueue(this);
            final StringRequest strFrente = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    txtStatusCmd.setText(response);
                    cont=0;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (cont > 0) {
                        txtStatusCmd.setText("Erro: " + error);
                        cont=0;
                    } else try {
                        Thread.sleep(500);
                        cont++;
                      //  frente();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        txtMsgServer.setText("Erro: " + e);
                    }

                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strFrente);
        }

        public void tras(){
            String url = "http://"+IP+"/b";

            //RequestQueue    myRequest = Volley.newRequestQueue(this);

            final StringRequest strTras = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    txtStatusCmd.setText(response);
                    cont=0;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (cont > 0) {
                        txtStatusCmd.setText("Erro: " + error);
                        cont=0;
                    } else try {
                        Thread.sleep(500);
                        cont++;
                      //  tras();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        txtMsgServer.setText("Erro: " + e);
                    }
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strTras);
        }

        public void esquerda(){
            String url = "http://"+IP+"/e";

            //RequestQueue   myRequest = Volley.newRequestQueue(this);

            final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    txtStatusCmd.setText(response);
                    cont=0;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (cont > 0) {
                        txtStatusCmd.setText("Erro: " + error);
                        cont=0;
                    } else try {
                        Thread.sleep(500);
                        cont++;
                      //  esquerda();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        txtMsgServer.setText("Erro: " + e);
                    }
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strSpeed);

        }

        public void direita(){
            String url = "http://"+IP+"/r";

            final StringRequest strSpeed = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    txtStatusCmd.setText(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txtStatusCmd.setText("Erro: "+error);
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(strSpeed);

        }

}
