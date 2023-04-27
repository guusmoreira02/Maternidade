package br.com.dlweb.maternidade.medico;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dlweb.maternidade.R;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MedicoViewHolder>{
    private List<Medico> medicos;
    private List<String> medicosIds;
    private FragmentActivity activity;

    MedicoAdapter(List<Medico> medicos, List<String> medicosIds, FragmentActivity activity){
        this.medicos = medicos;
        this.medicosIds = medicosIds;
        this.activity = activity;
    }

    class MedicoViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeView;
        private TextView celularView;

        MedicoViewHolder(View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.tvListMedicoNome);
            celularView = itemView.findViewById(R.id.tvListMedicoCelular);
        }

    }

    @Override
    public MedicoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.medico_item, parent, false);
        return new MedicoViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MedicoViewHolder viewHolder, int i) {
        final String id = medicosIds.get(i);
        viewHolder.nomeView.setText(medicos.get(i).getNome());
        viewHolder.celularView.setText(medicos.get(i).getCelular());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("id", id);
                EditarFragment editarFragment = new EditarFragment();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                editarFragment.setArguments(b);
                ft.replace(R.id.frameMedico, editarFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

}
