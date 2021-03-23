package com.brsan7.imc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brsan7.imc.application.HistoricoApplication
import com.brsan7.imc.model.HistoricoVO

class HistoricoViewModel : ViewModel() {
    private var fstScan: Boolean = true
    private var buscaPorData: Boolean = true
    private var _hAdapterList = MutableLiveData<List<HistoricoVO>>()
    val hAdapterList: LiveData<List<HistoricoVO>>
        get() = _hAdapterList

    fun buscarRegistros (argumento:String, isBuscaPorData:Boolean){
        if (fstScan || argumento != "") {
            buscaPorData = isBuscaPorData
            Thread{
                try {
                    _hAdapterList.postValue(HistoricoApplication.instance.helperDB?.buscarRegistros(argumento, isBuscaPorData))
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }.start()
            fstScan = false
        }
    }
    fun excluirRegistro (dataSelecionada: String,index: Int){

        Thread{
            try {
                HistoricoApplication.instance.helperDB?.deletarRegistro(index)
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
            if (buscaPorData) {
                buscarRegistros(dataSelecionada, true)
            }
            else{
                fstScan = true
                buscarRegistros("", false)
            }
        }.start()
    }
}