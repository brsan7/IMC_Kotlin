package com.brsan7.imc

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.brsan7.imc.model.HistoricoVO
import com.brsan7.imc.viewmodels.HistoricoEditViewModel

class HistoricoEditDialog : DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var heViewModel: HistoricoEditViewModel
    private var idHistorico: Int = 0
    private lateinit var tvDataDiag: TextView
    private lateinit var tvHoraDiag: TextView
    private lateinit var tvPesoDiag: TextView
    private lateinit var tvAlturaDiag: TextView
    private lateinit var tvAGeneroDiag: TextView
    private lateinit var etObservacaoDiag: EditText

    companion object{
        private const val EXTRA_ID = "id"

        fun newInstance(id: Long): HistoricoEditDialog{

            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)
            val historicoEditFragment = HistoricoEditDialog()
            historicoEditFragment.arguments = bundle
            return historicoEditFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity?.layoutInflater?.inflate(R.layout.activity_historico_edit, null)
        idHistorico = arguments?.getLong(EXTRA_ID)?.toInt() ?: 0
        tvDataDiag = view?.findViewById(R.id.tvDataEdit) as TextView
        tvHoraDiag = view.findViewById(R.id.tvHoraEdit) as TextView
        tvPesoDiag = view.findViewById(R.id.tvPesoEdit) as TextView
        tvAlturaDiag = view.findViewById(R.id.tvAlturaEdit) as TextView
        tvAGeneroDiag = view.findViewById(R.id.tvGeneroEdit) as TextView
        etObservacaoDiag = view.findViewById(R.id.etObservacaoEdit) as EditText

        setupHistoricoEditViewModel()

        return AlertDialog.Builder(activity as Activity)
                .setTitle(getString(R.string.historicoEditTitulo))
                .setView(view)
                .setNeutralButton(getString(R.string.btnVoltarHistoricoEdit),this)
                .setPositiveButton(getString(R.string.btnSalvarHistoricoEdit),this)
                .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {

        if(which==-1){onClickSalvarRegistro()}
    }

    private fun setupHistoricoEditViewModel(){

        heViewModel = ViewModelProvider(this).get(HistoricoEditViewModel::class.java)
        heViewModel.heItemHist.observe(this, { itemHist->
            setupRegistro(itemHist)
        })
        heViewModel.getItemEdit(idHistorico)
    }

    override fun onPause() {
        super.onPause()
        heViewModel.heItemHist.value?.observacao = etObservacaoDiag.text.toString()
    }

    private fun setupRegistro(itemHist: HistoricoVO){

        tvDataDiag.text = itemHist.data
        tvHoraDiag.text = itemHist.hora
        tvPesoDiag.text = itemHist.peso
        tvAlturaDiag.text = itemHist.altura
        tvAGeneroDiag.text = itemHist.genero
        etObservacaoDiag.setText(itemHist.observacao)
    }

    private fun onClickSalvarRegistro(){

        val itemHist = HistoricoVO(
                id = idHistorico,
                data = tvDataDiag.text.toString(),
                hora = tvHoraDiag.text.toString(),
                peso = tvPesoDiag.text.toString(),
                altura = tvAlturaDiag.text.toString(),
                genero = tvAGeneroDiag.text.toString(),
                observacao = etObservacaoDiag.text.toString()
        )
        heViewModel.editItem(itemHist)
    }
}