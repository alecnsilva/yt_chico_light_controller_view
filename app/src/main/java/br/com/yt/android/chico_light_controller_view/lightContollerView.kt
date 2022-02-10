package br.com.yt.android.chico_light_controller_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class lightContollerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var barWidth = 0.0f
    private var barHeight = 0.0f
    private val label_X_OFFSET = 20
    private val PADDING_OFFSET = 27
    private val pointPosition = PointF(0.0f, 0.0f)
    private var controllerSetting = ControllerSetting.OFF

    // Importante fazer as inicializações fora do método onDraw
    // para não ter problemas de desempenho nas View
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.LEFT
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Para melhor ajuste de tela da View, temos que dividir o valor das barras por 2
        // e converter em Float, já que o resultado tem que ficar com o tipo declarado
        barWidth = (w / 2).toFloat()
        barHeight = (h / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // definir uma cor para o paint
        paint.color = Color.GRAY

        // Desnhar um retangulo descontando o paddingOffset
        canvas?.drawRect(
            pointPosition.x + barWidth / 2,
            pointPosition.y + PADDING_OFFSET,
            (pointPosition.x + barWidth * 1.5).toFloat(),
            pointPosition.y + barHeight - PADDING_OFFSET,
            paint
        )
    }

    enum class ControllerSetting(val label: Int) {
        OFF(label = 0),
        TWENTY(label = 25),
        FIFTY(label = 50),
        SEVENTY_FIFTY(label = 75),
        FULL(label = 100);
    }
}
