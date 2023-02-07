package com.example.makemon.ui.character_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.makemon.R
import com.example.makemon.adapter.ViewPagerThreeAdapter
import com.example.makemon.databinding.FragmentCharacterBinding
import com.example.makemon.ui.MainActivity

class CharacterListThreeFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "CharacterListThreeFragment"
    }

    lateinit var binding: FragmentCharacterBinding

    private var pagerAdapter: ViewPagerThreeAdapter? = null

    private var pageIndex: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        with(requireActivity() as MainActivity) {
            bottomNavigationVisibility(false)
            setToolbarBackVisibility(true)
        }

        binding.btMove.setOnClickListener(this)

        binding.viewPager.apply {
            /*offscreenPageLimit = 3
            setPadding(50, 0, 50, 0)*/

            pagerAdapter = ViewPagerThreeAdapter()
            this.adapter = pagerAdapter
            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    Log.w("CharacterListOneFragment", "position:: $position")
                    if (position.toString().toInt() > 29) {
                        binding.textPage.text = String.format(getString(R.string.viewpager_banner_text), position - 29, pagerAdapter!!.data.size)
                    } else {
                        binding.textPage.text = String.format(getString(R.string.viewpager_banner_text), position + 1, pagerAdapter!!.data.size)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }

        binding.editPage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                pageIndex = s.toString()
                if (s.toString().length > 2) {
                    binding.textInputLayer.error = "2자리 까지 입력 가능합니다."
                    binding.btMove.isClickable = false
                } else if (s.toString().length >= 2 && s.toString() > 30.toString()) {
                    binding.textInputLayer.error = "입력이 가능한 값은 30이 최대입니다"
                    binding.btMove.isClickable = false
                }
                else {
                    binding.textInputLayer.error = null
                    binding.btMove.isClickable = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id) {
                R.id.btMove -> {
                    if (binding.editPage.text.toString().isEmpty()) return
                    binding.viewPager.currentItem = pageIndex.toInt() - 1
                }
            }
        }
    }
}