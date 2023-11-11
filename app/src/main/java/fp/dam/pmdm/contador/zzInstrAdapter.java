package fp.dam.pmdm.contador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class zzInstrAdapter extends ArrayAdapter<zzInstrucciones> {
    public zzInstrAdapter(@NonNull Context context, int resource, @NonNull List<zzInstrucciones> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        zzInstrucciones instr = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.zz_fila_listview, parent, false);
        ((TextView) convertView.findViewById(R.id.ItemTexto)).setText(instr.getPalabras());
        ((ImageView) convertView.findViewById(R.id.ItemFoto)).setImageResource(instr.getImagen());
        return convertView;
    }
}
