package com.enrique.listusers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {

    private Context context;
    private List<Users> users;

    public UserAdapter(Context context, List<Users> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //INFLAR EL USER LAYOUT
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyHolder holder, int position) {
        //OBTENER LOS DATOS DEL MODELO
        String NOMBRES = users.get(position).getNOMBRES();
        String APELLIDOS = users.get(position).getAPELLIDOS();
        String CORREO = users.get(position).getCORREO();

        //SETEO DE CAMPOS
        holder.NombreList.setText(NOMBRES + " " + APELLIDOS);
        holder.CorreoList.setText(CORREO);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public  class  MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView NombreList, CorreoList;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            NombreList = itemView.findViewById(R.id.NombreList);
            CorreoList = itemView.findViewById(R.id.CorreoList);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String uid = users.get(position).getUID();
            String nombre = users.get(position).getNOMBRES();
            String apellidos = users.get(position).getAPELLIDOS();
            String correo = users.get(position).getCORREO();
            String pass = users.get(position).getPASSWORD();
            Integer edad = users.get(position).getEDAD();

            Intent intent = new Intent(itemView.getContext(), RegisterUsers.class);
            intent.putExtra("UID", uid);
            intent.putExtra("NOMBRES", nombre);
            intent.putExtra("APELLIDOS", apellidos);
            intent.putExtra("CORREO", correo);
            intent.putExtra("PASSWORD", pass);
            intent.putExtra("EDAD", edad.toString());
            itemView.getContext().startActivity(intent);
        }
    }
}
