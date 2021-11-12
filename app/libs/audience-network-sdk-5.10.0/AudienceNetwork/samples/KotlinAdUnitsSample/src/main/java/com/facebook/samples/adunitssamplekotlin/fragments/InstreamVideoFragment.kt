/*
 * Copyright (c) 2004-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.facebook.samples.adunitssamplekotlin.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.ads.*
import com.facebook.samples.adunitssamplekotlin.R

class InstreamVideoFragment : Fragment(), InstreamVideoAdListener {

  private var instreamVideoAdStatusLabel: TextView? = null
  private var showInstreamVideoButton: Button? = null
  private var adViewContainer: RelativeLayout? = null
  private var instreamVideoAdView: InstreamVideoAdView? = null

  private var isShowClicked = false

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_instream_video, container, false)

    instreamVideoAdStatusLabel = view.findViewById(R.id.instreamVideoAdStatusLabel)
    val loadInstreamVideoButton = view.findViewById<Button>(R.id.loadInstreamVideoButton)
    showInstreamVideoButton = view.findViewById(R.id.showInstreamVideoButton)
    val destroyInstreamVideoButton = view.findViewById<Button>(R.id.destroyInstreamVideoButton)
    adViewContainer = view.findViewById(R.id.adViewContainer)

    loadInstreamVideoButton.setOnClickListener {
      isShowClicked = false
      if (instreamVideoAdView != null) {
        instreamVideoAdView?.destroy()
        adViewContainer?.removeAllViews()
      }
      instreamVideoAdView =
          InstreamVideoAdView(
              this.activity,
              "YOUR_PLACEMENT_ID",
              AdSize(
                  (adViewContainer!!.measuredWidth * DENSITY).toInt(),
                  (adViewContainer!!.measuredHeight * DENSITY).toInt()))
      instreamVideoAdView?.loadAd(
          instreamVideoAdView?.buildLoadAdConfig()?.withAdListener(this)?.build())

      setStatusLabelText("Loading Instream video ad...")
    }

    showInstreamVideoButton?.setOnClickListener {
      if (instreamVideoAdView == null || !instreamVideoAdView!!.isAdLoaded) {
        setStatusLabelText("Ad not loaded. Click load to request an ad.")
      } else {
        if (instreamVideoAdView!!.parent !== adViewContainer) {
          adViewContainer?.addView(instreamVideoAdView)
        }
        instreamVideoAdView!!.show()
        setStatusLabelText("Ad Showing")
        isShowClicked = true
      }
    }

    destroyInstreamVideoButton.setOnClickListener {
      if (instreamVideoAdView != null) {
        instreamVideoAdView!!.destroy()
        instreamVideoAdView = null
        adViewContainer?.removeAllViews()
        setStatusLabelText("Ad destroyed")
      }
    }

    // Restore state
    if (savedInstanceState == null) {
      return view
    }
    val adState = savedInstanceState.getBundle(AD)
    if (adState != null) {
      instreamVideoAdView = InstreamVideoAdView(this.activity, adState)
      instreamVideoAdView?.loadAd(
          instreamVideoAdView?.buildLoadAdConfig()
              ?.withAdListener(
                  object : InstreamVideoAdListener {
                    override fun onAdVideoComplete(ad: Ad) {
                      this@InstreamVideoFragment.onAdVideoComplete(ad)
                    }

                    override fun onError(ad: Ad, error: AdError) {
                      this@InstreamVideoFragment.onError(ad, error)
                    }

                    override fun onAdLoaded(ad: Ad) {
                      this@InstreamVideoFragment.onAdLoaded(ad)
                      showInstreamVideoButton?.callOnClick()
                    }

                    override fun onAdClicked(ad: Ad) {
                      this@InstreamVideoFragment.onAdClicked(ad)
                    }

                    override fun onLoggingImpression(ad: Ad) {
                      this@InstreamVideoFragment.onLoggingImpression(ad)
                    }
                  })
              ?.build())
    }

    return view
  }

  override fun onError(ad: Ad, error: AdError) {
    if (ad === instreamVideoAdView) {
      setStatusLabelText("Instream video ad failed to load: " + error.errorMessage)
    }
  }

  override fun onAdLoaded(ad: Ad) {
    if (ad === instreamVideoAdView) {
      setStatusLabelText("Ad loaded. Click show to present!")
    }
  }

  override fun onAdClicked(ad: Ad) {
    Toast.makeText(this.activity, "Instream Video Clicked", Toast.LENGTH_SHORT).show()
  }

  override fun onLoggingImpression(ad: Ad) {
    Log.d(TAG, "onLoggingImpression")
  }

  private fun setStatusLabelText(label: String) {
    instreamVideoAdStatusLabel?.text = label
  }

  override fun onAdVideoComplete(ad: Ad) {
    Toast.makeText(this.activity, "Instream Video Completed", Toast.LENGTH_SHORT).show()
    adViewContainer?.removeView(instreamVideoAdView)
    isShowClicked = false
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    if (instreamVideoAdView == null) {
      return
    }
    val adState = instreamVideoAdView!!.saveInstanceState
    if (adState != null) {
      outState.putBundle(AD, adState)
    }
  }

  override fun onStop() {
    if (instreamVideoAdView != null) {
      adViewContainer?.removeView(instreamVideoAdView)
    }
    super.onStop()
  }

  override fun onResume() {
    if (isShowClicked &&
        instreamVideoAdView != null &&
        instreamVideoAdView!!.parent !== adViewContainer) {
      adViewContainer?.addView(instreamVideoAdView)
    }
    super.onResume()
  }

  companion object {

    private val TAG = InstreamVideoFragment::class.java.simpleName
    private const val AD = "ad"

    private val DENSITY = Resources.getSystem().displayMetrics.density.toDouble()
  }
}
