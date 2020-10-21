package es.iessaladillo.pedrojoya.exchange

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import es.iessaladillo.pedrojoya.exchange.databinding.MainActivityBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toDollar.setEnabled(false)
        binding.fromEuro.setEnabled(false)
        setupViews()
    }
    private fun setupViews() {
        binding.txtAmount.setText("0")
        binding.txtAmount.setSelection(0, 1);
        binding.btnExchange.setOnClickListener { v: View? -> exchangeOnClick() }
        binding.buttonFromCurrency.setOnCheckedChangeListener { v: View?, checkedId: Int? -> btnFromOnClick()}
        binding.buttonToCurrency.setOnCheckedChangeListener { v: View?, checkedId: Int? -> btnToOnClick() }
        binding.txtAmount.doAfterTextChanged { txtOnChange()  }
        binding.txtAmount.setOnEditorActionListener{v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                exchangeOnClick()
                false
            } else{
                true
            }
        }

    }
    private fun exchangeOnClick() {
        val moneyFrom : Currency
        val  moneyTo : Currency
        if(binding.fromDollar.isChecked)
            moneyFrom = Currency.DOLLAR
        else if (binding.fromEuro.isChecked)
            moneyFrom = Currency.EURO
        else
            moneyFrom = Currency.POUND
        if(binding.toDollar.isChecked)
            moneyTo = Currency.DOLLAR
        else if (binding.toEuro.isChecked)
            moneyTo = Currency.EURO
        else{
            moneyTo = Currency.POUND
        }



        SoftInputUtils.hideSoftKeyboard(binding.txtAmount)
        val df = DecimalFormat("#.00")
        Toast.makeText(
            this,
            df.format(binding.txtAmount.text.toString().toDouble()) + moneyFrom.symbol + "=" + df.format(moneyTo.fromDollar(
                moneyFrom.toDollar(
                    binding.txtAmount.text.toString().toDouble()
                )
            )) + moneyTo.symbol,
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun btnFromOnClick() {
        if(binding.fromDollar.isChecked){
            binding.fromLogo.setImageResource(R.drawable.ic_dollar)
            binding.toDollar.setEnabled(false)
            binding.toEuro.setEnabled(true)
            binding.toPound.setEnabled(true)
        }
        else if (binding.fromEuro.isChecked){
            binding.fromLogo.setImageResource(R.drawable.ic_euro)
            binding.toEuro.setEnabled(false)
            binding.toDollar.setEnabled(true)
            binding.toPound.setEnabled(true)
        } else{
            binding.fromLogo.setImageResource(R.drawable.ic_pound)
            binding.toEuro.setEnabled(true)
            binding.toDollar.setEnabled(true)
            binding.toPound.setEnabled(false)
        }



    }

    private fun btnToOnClick() {
        if(binding.toDollar.isChecked){
            binding.toLogo.setImageResource(R.drawable.ic_dollar)
            binding.fromDollar.setEnabled(false)
            binding.fromEuro.setEnabled(true)
            binding.fromPound.setEnabled(true)
        }
        else if (binding.toEuro.isChecked){
            binding.toLogo.setImageResource(R.drawable.ic_euro)
            binding.fromEuro.setEnabled(false)
            binding.fromDollar.setEnabled(true)
            binding.fromPound.setEnabled(true)
        } else{
            binding.toLogo.setImageResource(R.drawable.ic_pound)
            binding.fromEuro.setEnabled(true)
            binding.fromDollar.setEnabled(true)
            binding.fromPound.setEnabled(false)
        }



    }

    private fun txtOnChange(){
        //Lo que hago despues de que se modifique el texto
        if(binding.txtAmount.text.toString() == "" ||  binding.txtAmount.text.toString()=="00" || binding.txtAmount.text.toString().get(0)=='.' ){
            binding.txtAmount.setText("0")
            binding.txtAmount.setSelection(0, 1);
        }
    }

}