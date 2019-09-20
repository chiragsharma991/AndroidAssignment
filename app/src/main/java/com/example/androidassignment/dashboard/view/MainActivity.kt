package com.example.androidassignment.dashboard.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.R
import com.example.androidassignment.helper.adapter.RecyclerCallback
import com.example.androidassignment.helper.adapter.UniversalAdapter
import com.example.androidassignment.base.BaseActivity
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.databinding.RowUserinfolistBinding
import com.example.androidassignment.dashboard.pojo.Row
import com.example.androidassignment.dashboard.pojo.UserInfomodel
import com.example.androidassignment.dashboard.viewmodel.UserInfoviewmodel
import com.example.androidassignment.helper.extention.hideView
import com.example.androidassignment.helper.extention.showView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {


    private lateinit var mAdapter: UniversalAdapter<Row, RowUserinfolistBinding>
    private lateinit var binding: ActivityMainBinding
    private var userinfo_list: ArrayList<UserInfomodel>? = null
    lateinit var mViewModel: UserInfoviewmodel

    companion object {


    }

    /**
     * @param TAG: activity tag to filter out any log statement
     */
    override fun TAG() = "MainActivity"

    /**
     * put your root layout here
     */
    override fun getLayout() = R.layout.activity_main


    /**
     * Initialise :
     *
     * @param viewDataBinding : view binding (root layout)
     */

    override fun initViews(viewDataBinding: ViewDataBinding, savedInstanceState: Bundle?) {
        binding = viewDataBinding as ActivityMainBinding
        binding.errorView.visibility= View.GONE
        binding.myswipeRefreshlayout.setOnRefreshListener { getUserInfo(true) }
        binding.retry.setOnClickListener{ getUserInfo(true) }
        initToolbar()
        initViewModel()

    }

    /**
     * Toolbar initialization
     */

    private fun initToolbar(){
        txt_title.text = ""
        img_back.setOnClickListener{onBackPressed()}
    }

    /**
     * Viewmodel initialization
     */

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(UserInfoviewmodel::class.java)
        binding.shimmerViewContainer.showView()
        binding.shimmerViewContainer.startShimmerAnimation()

        mViewModel.loadingVisibility.observe(this, Observer { anyprogress ->

            // any progressloader you want to show/hide

            error_view.visibility= View.GONE

            if(anyprogress){
                binding.myswipeRefreshlayout.isRefreshing = if(shimmer_view_container.visibility == View.GONE) true else false
            }else{
                binding.myswipeRefreshlayout.isRefreshing = false
                binding.shimmerViewContainer.hideView()
                binding.shimmerViewContainer.stopShimmerAnimation()
            }
        })
        getUserInfo(false)

    }


    /**
     * @param flag : If API call is forcefully applied (true/false)
     *
     * */

    fun getUserInfo(flag: Boolean) {

        mViewModel.getUserInfo(flag).observe(this, Observer { liveDataWrapper ->

            if (isSuccess(liveDataWrapper).first) {
                userinfo_list = ArrayList()
                userinfo_list!!.add(liveDataWrapper.data as UserInfomodel)
                onUIRefresh()
            }
        })

    }

    /**
     * error from API call
     * @param error_msg : error message
     */

    override fun isInternetConnect(error_msg : String) {
        super.isInternetConnect(error_msg)

            // show error : as you like

        binding.shimmerViewContainer.hideView()
        binding.shimmerViewContainer.stopShimmerAnimation()

        if(:: mAdapter.isInitialized){
            showError(error_msg)
        }else{
            binding.errorTxt.text=error_msg
            binding.errorView.visibility= View.VISIBLE
        }


    }


    /**
     * refresh any UI contain from here
     */

    private fun onUIRefresh() {

        // Refresh screen layout and parms.

        userinfo_list?.let {

            txt_title.text = userinfo_list!![0].title

            recyclerview.apply {

                if (::mAdapter.isInitialized) {

                    mAdapter.notifyDataSetChanged()

                } else {
                    mAdapter =
                        UniversalAdapter(this@MainActivity,
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
