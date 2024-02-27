package com.google.zxing.pdf417.encoder;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.epson.iprojection.ui.activities.presen.Defines;
import com.google.zxing.WriterException;
import com.google.zxing.pdf417.PDF417Common;
import com.serenegiant.camera.CameraConst;
import com.serenegiant.common.BuildConfig;
import com.serenegiant.widget.ProgressView;
import javassist.bytecode.Opcode;
import javassist.compiler.TokenId;

/* loaded from: classes2.dex */
final class PDF417ErrorCorrection {
    private static final int[][] EC_COEFFICIENTS = {new int[]{27, 917}, new int[]{522, 568, 723, 809}, new int[]{237, 308, 436, 284, 646, 653, 428, 379}, new int[]{274, 562, 232, 755, 599, 524, 801, 132, 295, 116, 442, 428, 295, 42, 176, 65}, new int[]{TokenId.OR_E, 575, 922, 525, 176, 586, CameraConst.DEFAULT_WIDTH, TokenId.IMPLEMENTS, 536, 742, 677, 742, 687, 284, 193, 517, 273, 494, 263, 147, 593, Defines.PDF_MAX_H, 571, TokenId.IF, 803, 133, 231, 390, 685, TokenId.PRIVATE, 63, TokenId.TRUE}, new int[]{539, TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE, 6, 93, 862, 771, 453, 106, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 287, 107, TypedValues.PositionType.TYPE_SIZE_PERCENT, 733, 877, BuildConfig.VERSION_CODE, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID, 723, 476, 462, 172, 430, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 858, 822, 543, 376, FrameMetricsAggregator.EVERY_DURATION, TokenId.Identifier, 672, 762, 283, 184, 440, 35, 519, 31, 460, 594, 225, 535, 517, TokenId.AND_E, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 158, 651, Opcode.JSR_W, 488, TypedValues.PositionType.TYPE_DRAWPATH, 648, 733, 717, 83, TokenId.FloatConstant, 97, 280, 771, 840, 629, 4, BuildConfig.VERSION_CODE, 843, 623, 264, 543}, new int[]{521, 310, 864, 547, 858, 580, 296, 379, 53, 779, 897, 444, TokenId.Identifier, 925, 749, 415, 822, 93, 217, 208, PDF417Common.MAX_CODEWORDS_IN_BARCODE, 244, 583, 620, 246, 148, 447, 631, 292, 908, 490, TypedValues.TransitionType.TYPE_AUTO_TRANSITION, 516, 258, 457, 907, 594, 723, 674, 292, 272, 96, 684, 432, 686, TypedValues.MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO, 860, 569, 193, 219, 129, 186, 236, 287, 192, 775, 278, 173, 40, 379, 712, 463, 646, 776, 171, 491, 297, 763, 156, 732, 95, ProgressView.DIRECTION_TOP_TO_BOTTOM, 447, 90, TypedValues.PositionType.TYPE_PERCENT_Y, 48, 228, 821, 808, 898, 784, 663, 627, 378, 382, 262, 380, TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE, 754, TokenId.SUPER, 89, 614, 87, 432, 670, 616, 157, 374, 242, 726, 600, 269, 375, 898, 845, 454, TokenId.PLUS_E, 130, 814, 587, 804, 34, 211, TokenId.PRIVATE, 539, 297, 827, 865, 37, 517, 834, 315, 550, 86, 801, 4, 108, 539}, new int[]{524, 894, 75, 766, 882, 857, 74, 204, 82, 586, 708, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, TypedValues.Custom.TYPE_DIMENSION, 786, 138, 720, 858, 194, 311, 913, 275, 190, 375, 850, 438, 733, 194, 280, Opcode.JSR_W, 280, 828, 757, 710, 814, 919, 89, 68, 569, 11, 204, 796, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 540, 913, 801, TypedValues.TransitionType.TYPE_DURATION, 799, 137, 439, 418, 592, 668, TokenId.MUL_E, 859, TokenId.ARSHIFT, 694, TokenId.INTERFACE, 240, 216, 257, 284, 549, 209, 884, 315, 70, TokenId.PACKAGE, 793, 490, 274, 877, 162, 749, 812, 684, 461, TokenId.SHORT, 376, 849, 521, 307, 291, 803, 712, 19, TokenId.EQ, 399, 908, 103, FrameMetricsAggregator.EVERY_DURATION, 51, 8, 517, 225, 289, 470, 637, 731, 66, 255, 917, 269, 463, 830, 730, 433, 848, 585, 136, 538, TypedValues.Custom.TYPE_REFERENCE, 90, 2, 290, 743, 199, 655, TypedValues.Custom.TYPE_STRING, TokenId.PACKAGE, 49, 802, 580, TokenId.MINUS_E, 588, 188, 462, 10, 134, 628, TokenId.IF, 479, 130, 739, 71, 263, 318, 374, 601, 192, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 142, 673, 687, 234, 722, 384, 177, 752, TypedValues.MotionType.TYPE_PATHMOTION_ARC, CameraConst.DEFAULT_WIDTH, 455, 193, 689, TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, 805, 641, 48, 60, 732, 621, 895, 544, 261, 852, 655, 309, 697, 755, 756, 60, 231, 773, 434, TypedValues.CycleType.TYPE_WAVE_SHAPE, 726, 528, TypedValues.PositionType.TYPE_PERCENT_WIDTH, 118, 49, 795, 32, 144, 500, 238, 836, 394, 280, 566, TokenId.GOTO, 9, 647, 550, 73, 914, TokenId.TRANSIENT, 126, 32, 681, TokenId.PROTECTED, 792, 620, 60, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 441, 180, 791, 893, 754, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, 383, 228, 749, 760, 213, 54, 297, 134, 54, 834, 299, 922, 191, 910, 532, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, 829, 189, 20, 167, 29, 872, 449, 83, 402, 41, 656, TypedValues.PositionType.TYPE_SIZE_PERCENT, 579, 481, 173, TokenId.FloatConstant, 251, 688, 95, 497, 555, 642, 543, 307, 159, 924, 558, 648, 55, 497, 10}, new int[]{TokenId.AND_E, 77, 373, TypedValues.PositionType.TYPE_PERCENT_HEIGHT, 35, 599, 428, 207, 409, 574, 118, 498, 285, 380, TokenId.NEQ, 492, 197, 265, 920, 155, 914, 299, 229, 643, 294, 871, 306, 88, 87, 193, TokenId.AND_E, 781, 846, 75, TokenId.NATIVE, 520, 435, 543, 203, 666, 249, TokenId.WHILE, 781, 621, CameraConst.DEFAULT_WIDTH, 268, 794, 534, 539, 781, 408, 390, 644, 102, 476, 499, 290, 632, 545, 37, 858, 916, 552, 41, 542, 289, 122, 272, 383, Defines.PDF_MAX_H, 485, 98, 752, 472, 761, 107, 784, 860, 658, 741, 290, 204, 681, 407, 855, 85, 99, 62, 482, 180, 20, 297, 451, 593, 913, 142, 808, 684, 287, 536, 561, 76, 653, 899, 729, 567, 744, 390, InputDeviceCompat.SOURCE_DPAD, 192, 516, 258, 240, 518, 794, 395, 768, 848, 51, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 384, 168, 190, 826, TokenId.NEW, 596, 786, 303, 570, BuildConfig.VERSION_CODE, 415, 641, 156, 237, 151, 429, 531, 207, 676, 710, 89, 168, 304, 402, 40, 708, 575, 162, 864, 229, 65, 861, 841, 512, 164, 477, 221, 92, TokenId.EQ, 785, 288, TokenId.LE, 850, 836, 827, 736, TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, 94, 8, 494, 114, 521, 2, 499, 851, 543, 152, 729, 771, 95, 248, TokenId.OR_E, 578, TokenId.INSTANCEOF, 856, 797, 289, 51, 684, 466, 533, 820, 669, 45, TypedValues.Custom.TYPE_COLOR, 452, 167, TokenId.TRANSIENT, 244, 173, 35, 463, 651, 51, 699, 591, 452, 578, 37, 124, 298, TokenId.PUBLIC, 552, 43, 427, 119, 662, 777, 475, 850, 764, TokenId.LSHIFT, 578, 911, 283, 711, 472, TypedValues.CycleType.TYPE_EASING, 245, 288, 594, 394, FrameMetricsAggregator.EVERY_DURATION, TokenId.NATIVE, 589, 777, 699, 688, 43, 408, 842, 383, 721, 521, 560, 644, 714, 559, 62, 145, 873, 663, 713, 159, 672, 729, 624, 59, 193, 417, 158, 209, 563, 564, TokenId.TRY, 693, 109, TypedValues.MotionType.TYPE_DRAW_PATH, 563, TokenId.LSHIFT_E, 181, 772, 677, 310, 248, TokenId.MUL_E, 708, TokenId.TRUE, 579, 870, 617, 841, 632, 860, 289, 536, 35, 777, 618, 586, TypedValues.CycleType.TYPE_WAVE_OFFSET, 833, 77, 597, TokenId.WHILE, 269, 757, 632, 695, 751, TokenId.PROTECTED, 247, 184, 45, 787, 680, 18, 66, 407, TokenId.ANDAND, 54, 492, 228, 613, 830, 922, 437, 519, 644, TypedValues.Custom.TYPE_DIMENSION, 789, TypedValues.CycleType.TYPE_EASING, 305, 441, 207, 300, 892, 827, 141, 537, BuildConfig.VERSION_CODE, 662, InputDeviceCompat.SOURCE_DPAD, 56, 252, TokenId.THROWS, 242, 797, 838, 837, 720, 224, 307, 631, 61, 87, 560, 310, 756, 665, 397, 808, 851, 309, 473, 795, 378, 31, 647, 915, 459, 806, 590, 731, TypedValues.CycleType.TYPE_WAVE_PHASE, 216, 548, 249, TokenId.IMPLEMENTS, 881, 699, 535, 673, 782, 210, 815, TypedValues.Custom.TYPE_DIMENSION, 303, 843, 922, 281, 73, 469, 791, 660, 162, 498, 308, 155, TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE, 907, 817, 187, 62, 16, TypedValues.CycleType.TYPE_WAVE_PHASE, 535, TokenId.SUPER, 286, 437, 375, 273, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 296, 183, 923, 116, 667, 751, TokenId.MUL_E, 62, TokenId.RSHIFT, 691, 379, 687, 842, 37, TokenId.LE, 720, 742, TokenId.PRIVATE, 5, 39, 923, 311, TypedValues.CycleType.TYPE_WAVE_OFFSET, 242, 749, TokenId.IMPLEMENTS, 54, 669, 316, TokenId.TRANSIENT, 299, 534, 105, 667, 488, CameraConst.DEFAULT_WIDTH, 672, 576, 540, 316, 486, 721, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 46, 656, 447, 171, 616, 464, 190, 531, 297, TokenId.IMPLEMENTS, 762, 752, 533, 175, 134, 14, BuildConfig.VERSION_CODE, 433, 717, 45, 111, 20, 596, 284, 736, 138, 646, TokenId.FALSE, 877, 669, 141, 919, 45, 780, 407, 164, TokenId.PUBLIC, 899, 165, 726, 600, TokenId.INTERFACE, 498, 655, TokenId.LE, 752, 768, 223, 849, 647, 63, 310, 863, 251, TokenId.RSHIFT, 304, 282, 738, 675, TokenId.TRUE, 389, 244, 31, 121, 303, 263}};

    private PDF417ErrorCorrection() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getErrorCorrectionCodewordCount(int i) {
        if (i < 0 || i > 8) {
            throw new IllegalArgumentException("Error correction level must be between 0 and 8!");
        }
        return 1 << (i + 1);
    }

    static int getRecommendedMinimumErrorCorrectionLevel(int i) throws WriterException {
        if (i > 0) {
            if (i <= 40) {
                return 2;
            }
            if (i <= 160) {
                return 3;
            }
            if (i <= 320) {
                return 4;
            }
            if (i <= 863) {
                return 5;
            }
            throw new WriterException("No recommendation possible");
        }
        throw new IllegalArgumentException("n must be > 0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String generateErrorCorrection(CharSequence charSequence, int i) {
        int errorCorrectionCodewordCount = getErrorCorrectionCodewordCount(i);
        char[] cArr = new char[errorCorrectionCodewordCount];
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = errorCorrectionCodewordCount - 1;
            int charAt = (charSequence.charAt(i2) + cArr[i3]) % PDF417Common.NUMBER_OF_CODEWORDS;
            while (i3 >= 1) {
                cArr[i3] = (char) ((cArr[i3 - 1] + (929 - ((EC_COEFFICIENTS[i][i3] * charAt) % PDF417Common.NUMBER_OF_CODEWORDS))) % PDF417Common.NUMBER_OF_CODEWORDS);
                i3--;
            }
            cArr[0] = (char) ((929 - ((charAt * EC_COEFFICIENTS[i][0]) % PDF417Common.NUMBER_OF_CODEWORDS)) % PDF417Common.NUMBER_OF_CODEWORDS);
        }
        StringBuilder sb = new StringBuilder(errorCorrectionCodewordCount);
        for (int i4 = errorCorrectionCodewordCount - 1; i4 >= 0; i4--) {
            char c = cArr[i4];
            if (c != 0) {
                cArr[i4] = (char) (929 - c);
            }
            sb.append(cArr[i4]);
        }
        return sb.toString();
    }
}
