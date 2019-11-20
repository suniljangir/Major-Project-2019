package com.secure_messaging;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by Kshitij on 10/2/2018.
 */

public class Chattingcustomadapter extends ArrayAdapter {

    int resource;
    ArrayList<Chatpojo_firebase> arrayList;
    Context context;
    String senderid;
    static  int i=0;


    public Chattingcustomadapter(Context context, int resource, ArrayList<Chatpojo_firebase> arrayList,String senderid) {
        super(context, resource, arrayList);
        this.resource = resource;
        this.arrayList = arrayList;
        this.senderid= senderid;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, null, false);

        EmojiconTextView senderview = view.findViewById(R.id.sendermsg);
        EmojiconTextView recieverview = view.findViewById(R.id.receivermsg);
        ImageView senderImage = view.findViewById(R.id.senderimage);
        ImageView recieverImage = view.findViewById(R.id.receiverimage);
        Button btn_sender = view.findViewById(R.id.sender_button);
        Button btn_receiver = view.findViewById(R.id.receiver_button);
        Button sender_location = view.findViewById(R.id.sender_location);
        Button receiver_location = view.findViewById(R.id.receiver_location);
        Chatpojo_firebase pojo = arrayList.get(position);
        final String sendermsg = pojo.getSendermsg();
        final String messagetype = pojo.getMessagetype();
        String sid = pojo.getSenderid();
        i++;
        Log.d("senid",senderid+i);
        if(sid.equals(senderid))
        {
            if(messagetype.equals("text"))
            {

                senderview.setText(sendermsg);
                senderImage.setVisibility(view.GONE);
                recieverview.setVisibility(view.GONE);
                recieverImage.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);

            }
            else if(messagetype.equals("image"))
            {
                Log.d("imagepath",sendermsg);
                Picasso.get().load(sendermsg).into(senderImage);
                senderview.setVisibility(view.GONE);
                recieverview.setVisibility(view.GONE);
                recieverImage.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);

                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);

            }
            else if(messagetype.equals("audio"))
            {

                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                Log.d("audiopath",sendermsg);
                btn_sender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try
                        {
                            Log.d("media sound",sendermsg);


                            mediaPlayer.setDataSource(sendermsg);
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });

                            mediaPlayer.prepare();
                        }
                        catch (Exception e)
                        {
                            Log.d("error:" , "error occured");
                        }

                    }
                });



            }
            else
            {


                receiver_location.setVisibility(view.GONE);

                senderview.setVisibility(view.GONE);
                recieverview.setVisibility(view.GONE);
                recieverImage.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                sender_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);


                        i.setData(Uri.parse(sendermsg));
                        context.startActivity(i);
                    }
                });
            }

        }
        else
        {
            if(messagetype.equals("text"))
            {
                recieverview.setText(sendermsg);
                recieverImage.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);
            }
            else if(messagetype.equals("image"))
            {
                Picasso.get().load(sendermsg).into(recieverImage);
                recieverview.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);
            }
            else if(messagetype.equals("audio"))
            {
                btn_sender.setVisibility(view.GONE);
                sender_location.setVisibility(view.GONE);
                receiver_location.setVisibility(view.GONE);

                Log.d("audiopath",sendermsg);
                btn_receiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try
                        {
                            Log.d("media sound",sendermsg);
                            mediaPlayer.setDataSource(sendermsg);
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });

                            mediaPlayer.prepare();
                        }
                        catch (Exception e)
                        {
                            Log.d("error:" , "error occured");
                        }
                    }
                });

            }
            else
            {

                recieverview.setVisibility(view.GONE);
                senderview.setVisibility(view.GONE);
                btn_sender.setVisibility(view.GONE);
                btn_receiver.setVisibility(view.GONE);
                sender_location.setVisibility(view.GONE);

                receiver_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                             Intent i = new Intent(Intent.ACTION_VIEW);


                            i.setData(Uri.parse(sendermsg));
                            context.startActivity(i);
                    }
                });

            }

        }



        return view;
    }

}
