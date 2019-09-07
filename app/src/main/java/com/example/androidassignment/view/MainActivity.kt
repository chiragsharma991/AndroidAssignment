package com.example.androidassignment.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidassignment.R
import com.example.androidassignment.helper.adapter.adapter.RecyclerCallback
import com.example.androidassignment.helper.adapter.adapter.UniversalAdapter
import com.example.androidassignment.base.BaseActivity
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.databinding.RowUserinfolistBinding
import com.example.androidassignment.dataclass.Row
import com.example.androidassignment.dataclass.UserInfomodel
import com.example.androidassignment.helper.adapter.imageloader.ImageLoader
import com.example.androidassignment.viewmodel.UserInfoviewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {


    private lateinit var mAdapter: UniversalAdapter<Row, RowUserinfolistBinding>
    private lateinit var binding: ActivityMainBinding
    private var userinfo_list: ArrayList<UserInfomodel>? = null
    lateinit var mViewModel: UserInfoviewmodel


    companion object {

        fun newInstance(): MainActivity {
            return MainActivity()
        }
    }


    override fun TAG() = "MainActivity"


    override fun getLayout() = R.layout.activity_main


    override fun initViews(viewDataBinding: ViewDataBinding, savedInstanceState: Bundle?) {
        binding = viewDataBinding as ActivityMainBinding
        error_view.visibility= View.GONE
        mySwipeRefreshLayout.setOnRefreshListener { getUserInfo(true) }
        retry.setOnClickListener{ getUserInfo(true) }
        initToolbar()
        initViewModel()

    }


    private fun initToolbar(){
        txt_title.text = ""
        img_back.setOnClickListener{onBackPressed()}

    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(UserInfoviewmodel::class.java)
        mySwipeRefreshLayout.isRefreshing = true
        getUserInfo(false)
    }

    /**
     * @param flag : If API call is forcefully applied
     * */

    fun getUserInfo(flag: Boolean) {

        mViewModel.loadingVisibility.observe(this, Observer { anyprogress ->
            // show any progress here
            loge(TAG(),"progress--"+anyprogress)

        })

        mViewModel.getUserInfo(flag).observe(this, Observer { liveDataWrapper ->
            loge(TAG(), "refresh---")

            error_view.visibility= View.GONE
            mySwipeRefreshLayout.isRefreshing = false

            if (isSuccess(liveDataWrapper).first) {
                userinfo_list = ArrayList()
                userinfo_list!!.add(liveDataWrapper.data as UserInfomodel)
                onUIRefresh()
            }
        })

    }


    override fun isInternetConnect(error_msg : String) {
        super.isInternetConnect(error_msg)
            // show error : as you like
        if(:: mAdapter.isInitialized){
            showError(error_msg)
        }else{
            error_txt.text=error_msg
            error_view.visibility= View.VISIBLE

        }


    }


    private fun onUIRefresh() {
        // Refresh screen layout and parms.

        userinfo_list?.let {

            txt_title.text = userinfo_list!![0].title

            recyclerview.apply {

                if (::mAdapter.isInitialized) {

                    mAdapter.notifyDataSetChanged()

                } else {
                    mAdapter = UniversalAdapter(this@MainActivity,
                        userinfo_list!![0].rows, R.layout.row_userinfolist, object :
                            RecyclerCallback<RowUserinfolistBinding, Row> {
                            override fun bindData(binder: RowUserinfolistBinding, model: Row) {
                                binder.row = model
                                binder.executePendingBindings()
                            }
                        })
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = mAdapter
                }


            }


        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
