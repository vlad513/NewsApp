package com.example.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private val bundleArgs: DetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArg = bundleArgs.article

        articleArg.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(header_image)
            }
            header_image.clipToOutline = true
            article_details_title.text = article.title
            article_details_decription_text.text = article.description

            article_details_button.setOnClickListener {
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                            ?.let {
                                article.url } ?: "https://google.com"))
                        .let {
                            ContextCompat.startActivity(requireContext(), it, null)
                        }

                } catch (e: java.lang.Exception) {
                    Toast.makeText(context, "The device doesn't have any browser to view the document!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}