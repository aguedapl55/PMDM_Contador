package fp.dam.pmdm.contador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class zzUserAdapter extends RecyclerView.Adapter<zzUserAdapter.ViewHolder> {

    List<String> modeList;

    public zzUserAdapter(List<String> userModeList) {
        this.modeList = userModeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zz_fila_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(modeList.get(position));
    }

    @Override
    public int getItemCount() {
        return modeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombre;

        public ViewHolder(View v) {
            super (v);
            nombre = (TextView) v.findViewById(R.id.filaTexto);
        }
    }
}
