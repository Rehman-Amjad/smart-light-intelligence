package com.example.smartinteligentproject.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartinteligentproject.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private Context context;
    private List<User> mDatalist;

    public UserAdapter(Context context, List<User> mDatalist) {
        this.context = context;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);


        return new MyViewHolder(myview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user=mDatalist.get(position);


        String imgStr = user.getImg();
        if (imgStr != null && !imgStr.isEmpty()) {
            byte[] imageBytes = Base64.decode(imgStr, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(decodedImage);
        } else {
            holder.imageView.setImageDrawable(null);
        }


        holder.tvId.setText("ID: " + user.getId());

        holder.tvFire_Sensor.setText("1".equals(user.getT2FireSensor())
                ? "Fire Sensor: Fire Alert" : "Fire Sensor: No Fire");

        holder.tvFire_Sensor2.setText("1".equals(user.getT1FireSensor())
                ? "Fire Sensor: Fire Alert" : "Fire Sensor: No Fire");

        holder.tv_LDR.setText("1".equals(user.getT1UltrasonicSensor())
                ? "Light On" : "Light Off");

        holder.tv_LDR2.setText("1".equals(user.getT2UltrasonicSensor())
                ? "Light On" : "Light Off");

        holder.tvsrain_Sensor.setText("1".equals(user.getRain())
                ? "Rain Sensor: Its Raining" : "Rain Sensor: Its not Raining");



      /*  holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.tvId.getContext());
                builder.setTitle("Delete Teacher Data");
                builder.setMessage("Press Yes or No?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {



                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

       */




    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvId,tvFire_Sensor,tv_LDR,tvsrain_Sensor,tvFire_Sensor2,tv_LDR2;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.card_image);
            tvId=itemView.findViewById(R.id.tvId);
            tvFire_Sensor=itemView.findViewById(R.id.tvFire_Sensor);
            tv_LDR=itemView.findViewById(R.id.tv_LDR);
            tvsrain_Sensor=itemView.findViewById(R.id.tvrain_Sensor);
            tvFire_Sensor2=itemView.findViewById(R.id.tvFire_Sensor2);
            tv_LDR2=itemView.findViewById(R.id.tv_LDR2);




        }
    }
}


