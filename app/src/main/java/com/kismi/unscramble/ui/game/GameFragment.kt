package com.kismi.unscramble.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kismi.unscramble.R
import com.kismi.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


//bagian ini adalah class tempat game
class GameFragment : Fragment() {

    //digunakan untuk mengikat instance objek dengan akses ke tampilan
    private lateinit var binding: GameFragmentBinding

    //bagian ini untuk membuat viewmodel
    //bagian ini adalah fragmen pertama
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //pada bagian ini digunakan untuk mengembalikan instance objek
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //pada bagian ini digunakan untuk mengatur viewModel dan mengakses kesemua data di viewModel
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        //pada bagian ini digunakan untuk menentukan tampilan fragmen
        binding.lifecycleOwner = viewLifecycleOwner

        // pada bagian ini digunakan untuk menyiapkan tombol klik untuk meng-submit dan melewati
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
    }

    /*
    *pada bagian ini digunakan untuk memeriksa kata pengguna dan untuk meng-update skor yang sesuai
    * pada bagian ini digunakan untuk menampilkan kata acak berikutnya
    * pada bagian ini digunakan untuk menampilkan skor akhir
    * */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    /*
     *pada bagian ini digunakan untuk melewati kata tanpa mengubah skor
     * pada bagian ini digunakan untuk menampilkan jumlah kata yang dilewati
     * dan pada bagian ini menampilkan hasil skor akhir
     */
    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }

    /*
     * pada bagian ini digunakan untuk membuat dan menampilkan dialog dengan skor akhir
      */
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, viewModel.score.value))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.exit)) { _, _ ->
                    exitGame()
                }
                .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                    restartGame()
                }
                .show()
    }

    /*
     * pada bagian ini digunakan untuk mengatur data apabila mengulang game
     * dan memperbaharui tampilan dengan data baru
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    /*
     * pada bagian ini untuk mengatur jika ingin keluar dari permainan
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * pada bagian ini digunakan untuk Menyetel dan menyetel ulang status kesalahan bidang teks.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }
}
