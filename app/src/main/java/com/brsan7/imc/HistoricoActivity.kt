package com.brsan7.imc

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brsan7.imc.adapter.HistoricoAdapter
import com.brsan7.imc.model.HistoricoVO
import com.brsan7.imc.viewmodels.HistoricoViewModel
import kotlinx.android.synthetic.main.activity_historico.*

class HistoricoActivity : BaseActivity() {

    private lateinit var adapter : HistoricoAdapter
    lateinit var hViewModel : HistoricoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)
        setupToolBar(toolBar, getString(R.string.historicoTitulo),true)

        setupRecyclerView()
        setupHistoricoViewModel()
        setupCalendario()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_busca, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.menuFiltro ->{
                calBusca.visibility = View.VISIBLE
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView(){

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupHistoricoViewModel(){

        hViewModel = ViewModelProvider(this).get(HistoricoViewModel::class.java)
        hViewModel.hAdapterList.observe(this, { lista->
            getRegistrosSelecionados(lista)
        })
        carregamentoDados(true)
        hViewModel.buscarRegistros("",false)
    }

    private fun setupCalendario(){

        calBusca.setOnDateChangeListener{ _, ano, mes, dia ->
            onClickBuscaData(ano, mes, dia)
        }
        calBusca.visibility = View.GONE
    }

    private fun onClickItemRecyclerView(index: Int){

        val fragment = HistoricoEditDialog.newInstance(index.toLong())
        fragment.show(supportFragmentManager, "dialog")
    }

    private fun getRegistrosSelecionados(lista: List<HistoricoVO>){

        adapter = HistoricoAdapter(this,lista ,object : HistoricoClickedListener{
            override fun historicoClickedItem(index: Int) {onClickItemRecyclerView(index)}
            override fun historicoRemoveItem(index: Int) {hViewModel.excluirRegistro(lista.first().data,index)}
        })
        recyclerView.adapter = adapter
        carregamentoDados(false)
    }

    private fun onClickBuscaData(ano:Int, mes:Int, dia:Int) {

        carregamentoDados(true)
        hViewModel.buscarRegistros("$dia/${mes + 1}/$ano", true)
        Toast.makeText(this,getString(R.string.msgToastHistoricoBusca)+"$dia/${mes + 1}/$ano",Toast.LENGTH_SHORT).show()
    }

    private fun carregamentoDados(isLoading: Boolean){

        pbHistorico.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

