package com.brsan7.imc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brsan7.imc.application.HistoricoApplication
import com.brsan7.imc.model.HistoricoVO

class HistoricoEditViewModel : ViewModel() {
    private var _heItemHist = MutableLiveData<HistoricoVO>()
    val heItemHist: LiveData<HistoricoVO>
        get() = _heItemHist

    fun getItemEdit(idHistorico: Int){
        if (_heItemHist.value?.id == null) {
            Thread(Runnable {
                try {
                    val itemHist = HistoricoApplication.instance.helperDB?.buscarRegistros("$idHistorico", false)
                    if (itemHist != null) {
                        _heItemHist.postValue(itemHist.getOrNull(0) ?: return@Runnable)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }).start()
        }
    }

    fun editItem(itemHist: HistoricoVO){

        Thread{
            try {
                HistoricoApplication.instance.helperDB?.updateRegistro(itemHist)
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()
    }
}