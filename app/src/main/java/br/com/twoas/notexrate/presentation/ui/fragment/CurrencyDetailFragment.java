package br.com.twoas.notexrate.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.databinding.FragmentCurrencyDetailBinding;
import br.com.twoas.notexrate.network.dto.forex.ChartDataDTO;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;
import br.com.twoas.notexrate.utils.Utils;


public class CurrencyDetailFragment extends Fragment {

    public interface Listener {
        void onRefresh();

        void onEdit();
    }

    private CurrencyViewModel mViewModel;
    private FragmentCurrencyDetailBinding mBinding;
    private Listener mListener;

    public static CurrencyDetailFragment newInstance(Listener listener) {
        CurrencyDetailFragment fragment = new CurrencyDetailFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CurrencyViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCurrencyDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnRefresh.setOnClickListener(v -> onRefresh());
        mBinding.imgEdit.setOnClickListener(v -> onEdit());
        mListener.onRefresh();
        createChart();
    }

    @SuppressLint("SetTextI18n")
    public void loadData() {
        QuoteDTO quote = mViewModel.quote;
        if (quote != null) {
            mBinding.labelFrom.setText(quote.getFromCurrency() + " = ");
            mBinding.labelTo.setText(quote.getCurrency());
            mBinding.price.setText(Utils.bigDecimalToString(quote.getPrice()));
            if (BigDecimal.ZERO.compareTo(quote.getPriceChange()) >= 0) {
                mBinding.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_down);
                mBinding.priceChange.setTextColor(ContextCompat.getColor(requireContext(),R.color.green));
                mBinding.lblPercentage.setTextColor(ContextCompat.getColor(requireContext(),R.color.green));
            } else {
                mBinding.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_up);
                mBinding.priceChange.setTextColor(ContextCompat.getColor(requireContext(),R.color.red));
                mBinding.lblPercentage.setTextColor(ContextCompat.getColor(requireContext(),R.color.red));
            }
            mBinding.lblUpdatedAt.setText(Utils.formatDate(quote.getTimeLastUpdated()));
            mBinding.priceChange.setText(Utils.bigDecimalToString(quote.getPriceChange()));
            mBinding.lblPercentage.setText("("+Utils.bigDecimalToString(quote.getPriceChangePercent()
                    .setScale(2, RoundingMode.HALF_UP)) + "%)");
            mBinding.priceChange2.setText(Utils.bigDecimalToString(quote.getPriceChange()));
            mBinding.lblPriceChangePercent.setText(Utils.bigDecimalToString(quote.getPriceChangePercent()));
            mBinding.lastClose.setText(Utils.bigDecimalToString(quote.getPricePreviousClose()));
            mBinding.lastOpen.setText(Utils.bigDecimalToString(quote.getPriceDayOpen()));
            mBinding.minPrice.setText(Utils.bigDecimalToString(quote.getPriceDayLow()));
            mBinding.maxPrice.setText(Utils.bigDecimalToString(quote.getPriceDayHigh()));
            mBinding.lblAsk.setText(Utils.bigDecimalToString(quote.getPriceAsk()));
            mBinding.lblBid.setText(Utils.bigDecimalToString(quote.getPriceBid()));
            mBinding.lblLastClose.setText(Utils.formatDate(quote.getDatePreviousClose()));
            mBinding.lblLastUpdate.setText(Utils.formatDate(quote.getTimeLastUpdated()));
            if (mViewModel.currencyNotify != null) {
                mViewModel.currencyNotify.lastPrice = quote.getPrice();
                mViewModel.currencyNotify.lastPriceChange = quote.getPriceChange();
                mViewModel.currencyNotify.lastUpdate = quote.getTimeLastUpdated();
                if (!mViewModel.currencyNotify.isMaxAlarming() && !mViewModel.currencyNotify.isMinAlarming()) {
                    mBinding.imgAlertDetail.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.imgAlertDetail.setVisibility(View.VISIBLE);
                }
                mBinding.lblMaxAlarm.setTextColor(mBinding.textView26.getCurrentTextColor());
                mBinding.lblMinAlarm.setTextColor(mBinding.textView26.getCurrentTextColor());
                if (mViewModel.currencyNotify.isMinAlarming()) {
                    mBinding.imgAlertDetail.setImageResource(R.drawable.ic_arrow_drop_down);
                    mBinding.lblMinAlarm.setTextColor(ContextCompat.getColor(requireContext(),R.color.green));
                }
                if (mViewModel.currencyNotify.isMaxAlarming()) {
                    mBinding.imgAlertDetail.setImageResource(R.drawable.ic_arrow_drop_up);
                    mBinding.lblMaxAlarm.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
                }
                mBinding.lblMinAlarm.setText(Utils.bigDecimalToString(mViewModel.currencyNotify.minValueAlert));
                mBinding.lblMaxAlarm.setText(Utils.bigDecimalToString(mViewModel.currencyNotify.maxValueAlert));
            }
        }
    }

    private void createChart() {

        // no description text
        mBinding.chart.getDescription().setEnabled(false);

        // enable touch gestures
        mBinding.chart.setTouchEnabled(true);

//        mBinding.chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mBinding.chart.setDragEnabled(true);
        mBinding.chart.setScaleEnabled(true);
        mBinding.chart.setDrawGridBackground(true);
        mBinding.chart.setHighlightPerDragEnabled(true);

        // set an alternative background color
//        mBinding.chart.setBackgroundColor(Color.WHITE);
//        mBinding.chart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // get the legend (only possible after setting data)
        Legend l = mBinding.chart.getLegend();
        l.setEnabled(true);
//
        XAxis xAxis = mBinding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            @Override
            public String getFormattedValue(float value) {
                return mFormat.format(new Date((long) value));
            }
        });

        YAxis leftAxis = mBinding.chart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    }

    public void setChartData(ChartDataDTO chartData) {
        setData(chartData);
    }

    private void setData(ChartDataDTO chartData) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < chartData.getSeries().getTimeStamps().size(); i++) {
            values.add(new Entry((float) chartData.getSeries().getTimeStamps().get(i).getTime(),
                    chartData.getSeries().getPrices().get(i).floatValue()));
        }

        ArrayList<Entry> close = makeEntries(chartData.getPricePreviousClose(), chartData.getSeries().getStartTime(), chartData.getSeries().getEndTime());
        ArrayList<Entry> high = makeEntries(chartData.getSeries().getPriceHigh(), chartData.getSeries().getStartTime(), chartData.getSeries().getEndTime());
        ArrayList<Entry> low = makeEntries(chartData.getSeries().getPriceLow(), chartData.getSeries().getStartTime(), chartData.getSeries().getEndTime());

        // create a dataset and give it a type
        LineDataSet set1 = createDataBase(values, "Prices",
                getColor(
                        chartData.getSeries().getPrices().get(chartData.getSeries().getPrices().size() - 1),
                        chartData.getPricePreviousClose()));
        LineDataSet set2 = createDataBase(close, "Last Close", Color.rgb(0, 0, 255));
        LineDataSet set3 = createDataBase(high, "High Price", Color.rgb(255, 0, 0));
        LineDataSet set4 = createDataBase(low, "Low Price", Color.rgb(0, 255, 0));

        YAxis leftAxis = mBinding.chart.getAxisLeft();
        leftAxis.calculate(chartData.getSeries().getPriceLow().floatValue(), chartData.getSeries().getPriceHigh().floatValue());

        // create a data object with the data sets
        LineData data = new LineData(set2, set3, set4, set1);
//        data.setValueTextColor(Color.RED);
//        data.setValueTextSize(9f);
//        data.calcMinMaxY(chartData.getSeries().getStartTime().getTime(), chartData.getSeries().getEndTime().getTime());


        // set data
        mBinding.chart.setData(data);
//        mBinding.chart.getXAxis().calculate((float)chartData.getSeries().getStartTime().getTime(),(float) chartData.getSeries().getEndTime().getTime());
        mBinding.chart.getXAxis().setAxisMinimum((float) chartData.getSeries().getStartTime().getTime());
        mBinding.chart.getXAxis().setAxisMaximum((float) chartData.getSeries().getEndTime().getTime());
        mBinding.chart.getData().notifyDataChanged();
        mBinding.chart.notifyDataSetChanged();
        mBinding.chart.invalidate();
    }

    private int getColor(BigDecimal lastValue, BigDecimal lastOpen) {
        return lastValue.compareTo(lastOpen) <= 0 ? Color.rgb(0, 255, 0) : Color.rgb(255, 0, 0);
    }

    private ArrayList<Entry> makeEntries(BigDecimal price, Date start, Date end) {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry((float) start.getTime(), price.floatValue()));
        values.add(new Entry((float) end.getTime(), price.floatValue()));
        return values;
    }

    private LineDataSet createDataBase(ArrayList<Entry> values, String label, int color) {
        LineDataSet lineDataSet = new LineDataSet(values, label);
        lineDataSet.setAxisDependency(AxisDependency.LEFT);
        lineDataSet.setLabel(label);
        lineDataSet.setColor(color);
        lineDataSet.setValueTextColor(color);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawHorizontalHighlightIndicator(true);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(color);
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setDrawCircleHole(false);
        return lineDataSet;
    }

    private void onEdit() {
        mListener.onEdit();
    }

    private void onRefresh() {
        mListener.onRefresh();
    }
}