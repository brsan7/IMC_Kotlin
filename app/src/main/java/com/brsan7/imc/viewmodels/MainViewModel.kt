package com.brsan7.imc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brsan7.imc.R
import com.brsan7.imc.application.HistoricoApplication
import com.brsan7.imc.model.HistoricoVO
import com.brsan7.imc.model.RecursosStrings

class MainViewModel : ViewModel() {
    var mResultadoImc = MutableLiveData<String>().apply { value = "" }
    var mClassificacaoImc = MutableLiveData<String>().apply { value = "Peso Ideal" }
    var mFaixaPeso = MutableLiveData<String>().apply { value = "" }
    var mSilhueta = MutableLiveData<Int>().apply { value = R.drawable.m2 }
    var fstScan: Boolean = true

    private lateinit var recursosStr: RecursosStrings

    private fun Float.format(digits: Int) = "%.${digits}f".format(this).replace(',','.')

    fun selecionaGenero(seletorGenIn:String){
        if(seletorGenIn==recursosStr.str7){mSilhueta.value = R.drawable.f2}
        else{mSilhueta.value = R.drawable.m2}
        mClassificacaoImc.value = "Peso Ideal"
        mFaixaPeso.value = ""
        mResultadoImc.value = ""
        fstScan = false
    }

    fun getStringRes(recursosStringsIn : RecursosStrings){
        recursosStr = recursosStringsIn
    }

    fun calcularImc(peso: Float, altura: Float,seletorGen: String){
        val imc = peso / (altura * altura)
        val pesoIdealIni=(18.0*(altura*altura)).toFloat()
        val pesoIdealFim=(25.0*(altura*altura)).toFloat()
        val pesoSobrando=peso-pesoIdealFim
        val pesoFaltando=pesoIdealIni-peso

        mResultadoImc.value = "IMC: ${imc.format(2)}"
        when {
            imc<18.5->{
                mClassificacaoImc.value = recursosStr.str1_1+recursosStr.str2+pesoFaltando.format(2)+recursosStr.str3+recursosStr.str4
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f1}
                else{mSilhueta.value = R.drawable.m1}
            }
            imc>=18.5&&imc<25->{
                mClassificacaoImc.value = recursosStr.str2_1
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f2}
                else{mSilhueta.value = R.drawable.m2}
            }
            imc>=25.0&&imc<30->{
                mClassificacaoImc.value = recursosStr.str3_1+recursosStr.str2+pesoSobrando.format(2)+recursosStr.str3+recursosStr.str4
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f3}
                else{mSilhueta.value = R.drawable.m3}
            }
            imc>=30.0&&imc<35->{
                mClassificacaoImc.value = recursosStr.str4_1+recursosStr.str2+pesoSobrando.format(2)+recursosStr.str3+recursosStr.str4
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f4}
                else{mSilhueta.value = R.drawable.m4}
            }
            imc>=35.0&&imc<40->{
                mClassificacaoImc.value = recursosStr.str5_1+recursosStr.str2+pesoSobrando.format(2)+recursosStr.str3+recursosStr.str4
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f4}
                else{mSilhueta.value = R.drawable.m4}
            }
            imc>=40.0->{
                mClassificacaoImc.value = recursosStr.str6_1+recursosStr.str2+pesoSobrando.format(2)+recursosStr.str3+recursosStr.str4
                mFaixaPeso.value = recursosStr.str5+pesoIdealIni.format(2)+recursosStr.str6+pesoIdealFim.format(2)+recursosStr.str3
                if(seletorGen == recursosStr.str7){mSilhueta.value = R.drawable.f5}
                else{mSilhueta.value = R.drawable.m5}
            }
        }
    }

    fun calcularProgresso(ultimoItemReg: HistoricoVO,itemHist: HistoricoVO):String{
        return if(ultimoItemReg.peso.toFloat()<itemHist.peso.toFloat()){
                    recursosStr.str8+
                    (itemHist.peso.toFloat() - ultimoItemReg.peso.toFloat()).format(2)+
                    recursosStr.str3
                }
                else{
                    recursosStr.str9+
                    (ultimoItemReg.peso.toFloat() - itemHist.peso.toFloat()).format(2)+
                    recursosStr.str3
                }
    }

    fun salvarRegistro(itemHist: HistoricoVO){
        Thread {
            HistoricoApplication.instance.helperDB?.salvarRegistro(itemHist)
        }.start()

    }
}