package br.com.yt.android.chico_light_controller_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Light contoller view
 *
 * @constructor
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class lightContollerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * Bar width
     */
    private var barWidth = 0.0f

    /**
     * Bar height
     */
    private var barHeight = 0.0f

    /**
     * Label_x_o f f s e t
     */
    private val labelOffsetX = 20

    /**
     * P a d d i n g_o f f s e t
     */
    private val paddingOffset = 27

    /**
     * Point position
     */
    private val pointPosition = PointF(0.0f, 0.0f)

    /**
     * Controller setting
     */
    private var controllerSetting = ControllerSetting.TWENTY_FIVE

    /**
     * Paint
     * Importante fazer as inicializações fora do método onDraw
     * para não ter problemas de desempenho nas View
     */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.LEFT
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    /**
     * On size changed
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Para melhor ajuste de tela da View, temos que dividir o valor das barras por 2
        // e converter em Float, já que o resultado tem que ficar com o tipo declarado
        barWidth = (w / 2).toFloat()
        barHeight = (h / 1).toFloat()
    }

    /**
     * On draw
     *
     * @param canvas
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // definir uma cor para o paint
        paint.color = Color.WHITE

        // Desnhar um retangulo descontando o paddingOffset
        canvas?.drawRect(
            pointPosition.x + barWidth / 2,
            pointPosition.y + paddingOffset,
            (pointPosition.x + barWidth * 1.5).toFloat(),
            pointPosition.y + barHeight - paddingOffset,
            paint
        )

        paint.color = Color.GREEN
        val indicator: RectF = pointPosition.createIndicatorRectF(
            controllerSetting,
            barWidth,
            barHeight
        )
        canvas?.drawRect(indicator, paint)

        paint.color = Color.BLACK

        // Percorrer os valores da enum e desenhar um label para cada um
        /**
         ControllerSetting.values().forEach {
         pointPosition.computeXYforSettingText(
         it,
         this.barWidth,
         this.barHeight
         )
         val label = it.label.toString()
         }*/

        // Alias para melhor entendimento - Semantico
        ControllerSetting.values().forEach { controllerSetting ->
            pointPosition.computeXYforSettingText(
                controllerSetting,
                this.barWidth,
                this.barHeight
            )
            val label = controllerSetting.label.toString()
            canvas?.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }
    // Função para definir o possicionamento dos LABELs
    private fun PointF.computeXYforSettingText(
        pos: ControllerSetting,
        barWidth: Float,
        height: Float
    ) {
        // X Posiciona os elementos 1.5 a mais do final (width) do canvas
        x = (1.39 * barWidth + labelOffsetX).toFloat()

        // Y Distribui os elementos em linhas sequentes
        val barHeight = height - 2 * paddingOffset

        y = when (pos.ordinal) {
            0 -> barHeight
            1 -> barHeight * 3 / 4
            2 -> barHeight / 2
            3 -> barHeight / 4
            4 -> 0.0f
            else -> { 0.0f }
        } + (1.5 * paddingOffset).toFloat()
    }

    private fun PointF.createIndicatorRectF(
        pos: ControllerSetting,
        width: Float,
        height: Float
    ): RectF {
        val left = x + width / 2
        val right = (x + width * 1.5).toFloat()
        val bottom = height - paddingOffset
        val barHeight = height - 2 * paddingOffset

        val top = when (pos.ordinal) {
            0 -> bottom
            1 -> bottom - barHeight / 4
            2 -> bottom - barHeight / 2
            3 -> bottom - barHeight * 3 / 4
            4 -> 0.0f + paddingOffset
            else -> { 0.0f }
        }

        return RectF(left, top, right, bottom)
    }

    /**
     * Controller setting
     *
     * @property label
     * @constructor Create empty Controller setting
     */
    enum class ControllerSetting(val label: Int) {
        /**
         * O f f
         *
         * @constructor Create empty O f f
         */
        OFF(label = 0),

        /**
         * T w e n t y
         *
         * @constructor Create empty T w e n t y
         */
        TWENTY_FIVE(label = 25),

        /**
         * F i f t y
         *
         * @constructor Create empty F i f t y
         */
        FIFTY(label = 50),

        /**
         * S e v e n t y_f i f t y
         *
         * @constructor Create empty S e v e n t y_f i f t y
         */
        SEVENTY_FIFTY(label = 75),

        /**
         * F u l l
         *
         * @constructor Create empty F u l l
         */
        FULL(label = 100);
    }
}
