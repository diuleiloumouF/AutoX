import { asGlobal } from "@/utils"

type Image = Autox.Image
type ImageFormat = 'png' | 'jpeg' | 'webp' | 'jpg'
type Color = string | number
type ImageFindOptions = {
    region?: [number, number, number?, number?],
    threshold?: number
}
type Point = any
var MatchingResult = (function () {
    var comparators: any = {
        "left": (l: Point, r: Point) => l.point.x - r.point.x,
        "top": (l: Point, r: Point) => l.point.y - r.point.y,
        "right": (l: Point, r: Point) => r.point.x - l.point.x,
        "bottom": (l: Point, r: Point) => r.point.y - l.point.y
    }
    class MatchingResult {
        matches: any[]
        __points__: any

        constructor(list: any) {
            if (Array.isArray(list)) {
                this.matches = list;
            } else {
                this.matches = runtime.bridges.toArray(list);
            }
        }
        get points() {
            if (typeof (this.__points__) == 'undefined') {
                this.__points__ = this.matches.map(m => m.point);
            }
            return this.__points__;
        }

        first() {
            if (this.matches.length == 0) {
                return null;
            }
            return this.matches[0];
        }
        last() {
            if (this.matches.length == 0) {
                return null;
            }
            return this.matches[this.matches.length - 1];
        }
        findMax(cmp: (a: any, b: any) => number) {
            if (this.matches.length == 0) {
                return null;
            }
            var target = this.matches[0];
            this.matches.forEach(m => {
                if (cmp(target, m) > 0) {
                    target = m;
                }
            });
            return target;
        }
        leftmost() {
            return this.findMax(comparators.left);
        }
        topmost() {
            return this.findMax(comparators.top);
        }
        rightmost() {
            return this.findMax(comparators.right);
        }
        bottommost() {
            return this.findMax(comparators.bottom);
        }
        worst() {
            return this.findMax((l, r) => l.similarity - r.similarity);
        }
        best() {
            return this.findMax((l, r) => r.similarity - l.similarity);
        }
        sortBy(cmp: any) {
            var comparatorFn: any = null;
            if (typeof (cmp) == 'string') {
                cmp.split("-").forEach(direction => {
                    var buildInFn = comparators[direction];
                    if (!buildInFn) {
                        throw new Error("unknown direction '" + direction + "' in '" + cmp + "'");
                    }
                    (function (fn) {
                        if (comparatorFn == null) {
                            comparatorFn = fn;
                        } else {
                            comparatorFn = (function (comparatorFn, fn) {
                                return function (l: unknown, r: unknown) {
                                    var cmpValue = comparatorFn(l, r);
                                    if (cmpValue == 0) {
                                        return fn(l, r);
                                    }
                                    return cmpValue;
                                }
                            })(comparatorFn, fn);
                        }
                    })(buildInFn);
                });
            } else {
                comparatorFn = cmp;
            }
            var clone = this.matches.slice();
            clone.sort(comparatorFn);
            return new MatchingResult(clone);
        }
    }
    return MatchingResult;
})();

function images() {
}


if (android.os.Build.VERSION.SDK_INT >= 21) {
    util.__assignFunctions__(runtime.images, images, ['captureScreen', 'read', 'copy', 'load', 'clip', 'pixel'])
}
const { Point, Point3, Rect, Algorithm, Scalar,
    Size, Core, CvException, CvType, TermCriteria, RotatedRect, Range
} = org.opencv.core
const Imgproc = org.opencv.imgproc.Imgproc
const { Mat } = com.stardust.autojs.core.opencv
const { ImageWrapper } = com.stardust.autojs.core.image
images.opencvImporter = {
    Point, Point3, Rect, Algorithm, Scalar,
    Size, Core, CvException, CvType, TermCriteria, RotatedRect, Range,
    Imgproc, Mat
}

const defaultColorThreshold = 4;

var colors = Object.create(runtime.colors);
colors.alpha = function (color: number | string) {
    color = parseColor(color);
    return color >>> 24;
}
colors.red = function (color: number | string) {
    color = parseColor(color);
    return (color >> 16) & 0xFF;
}
colors.green = function (color: number | string) {
    color = parseColor(color);
    return (color >> 8) & 0xFF;
}
colors.blue = function (color: number | string) {
    color = parseColor(color);
    return color & 0xFF;
}

colors.isSimilar = function (c1: Color, c2: Color, threshold?: number, algorithm?: string) {
    c1 = parseColor(c1);
    c2 = parseColor(c2);
    threshold = threshold == undefined ? 4 : threshold;
    algorithm = algorithm == undefined ? "diff" : algorithm;
    var colorDetector = getColorDetector(c1, algorithm, threshold);
    return colorDetector.detectsColor(colors.red(c2), colors.green(c2), colors.blue(c2));
}

var javaImages = runtime.images;

var colorFinder = javaImages.colorFinder;

images.requestScreenCapture = function (landscape: boolean) {
    const ScreenCapturer = com.stardust.autojs.core.image.capture.ScreenCapturer;
    var orientation = ScreenCapturer.ORIENTATION_AUTO;
    if (landscape === true) {
        orientation = ScreenCapturer.ORIENTATION_LANDSCAPE;
    }
    if (landscape === false) {
        orientation = ScreenCapturer.ORIENTATION_PORTRAIT;
    }
    return javaImages.requestScreenCapture(orientation);
}

images.save = function (img: Image, path: string, format?: ImageFormat, quality?: number) {
    format = format || "png";
    quality = quality == undefined ? 100 : quality;
    return javaImages.save(img, path, format, quality);
}
images.stopScreenCapturer = javaImages.stopScreenCapturer.bind(javaImages)
images.saveImage = images.save;

images.grayscale = function (img: Image, dstCn?: number): Image {
    return images.cvtColor(img, "BGR2GRAY", dstCn);
}

images.threshold = function (img: Image, threshold: number, maxVal: number, type?: string) {
    initIfNeeded();
    var mat = new Mat();
    type = type || "BINARY";
    type = Imgproc["THRESH_" + type];
    Imgproc.threshold(img.mat, mat, threshold, maxVal, type);
    return images.matToImage(mat);
}

images.inRange = function (img: Image, lowerBound: string | number, upperBound: number): Image {
    initIfNeeded();
    var lb = new Scalar(colors.red(lowerBound), colors.green(lowerBound),
        colors.blue(lowerBound), colors.alpha(lowerBound));
    var ub = new Scalar(colors.red(upperBound), colors.green(upperBound),
        colors.blue(upperBound), colors.alpha(lowerBound))
    var bi = new Mat();
    Core.inRange(img.mat, lb, ub, bi);
    return images.matToImage(bi);
}

images.interval = function (img: Image, color: Color, threshold: number) {
    initIfNeeded();
    var lb = new Scalar(colors.red(color) - threshold, colors.green(color) - threshold,
        colors.blue(color) - threshold, colors.alpha(color));
    var ub = new Scalar(colors.red(color) + threshold, colors.green(color) + threshold,
        colors.blue(color) + threshold, colors.alpha(color));
    var bi = new Mat();
    Core.inRange(img.mat, lb, ub, bi);
    return images.matToImage(bi);
}

images.adaptiveThreshold = function (img: Image, maxValue: number,
    adaptiveMethod: 'MEAN_C' | 'GAUSSIAN_C', thresholdType: 'BINARY' | 'BINARY_INV',
    blockSize: number, C: number) {
    initIfNeeded();
    var mat = new Mat();
    adaptiveMethod = Imgproc["ADAPTIVE_THRESH_" + adaptiveMethod];
    thresholdType = Imgproc["THRESH_" + thresholdType];
    Imgproc.adaptiveThreshold(img.mat, mat, maxValue, adaptiveMethod, thresholdType, blockSize, C);
    return images.matToImage(mat);
}
images.blur = function (img: Image, size: number[], point?: unknown[], type?: string) {
    initIfNeeded();
    var mat = new Mat();
    size = newSize(size);
    type = Core["BORDER_" + (type || "DEFAULT")];
    if (point == undefined) {
        Imgproc.blur(img.mat, mat, size);
    } else {
        Imgproc.blur(img.mat, mat, size, new Point(point[0], point[1]), type);
    }
    return images.matToImage(mat);
}

images.medianBlur = function (img: Image, size: number[]) {
    initIfNeeded();
    var mat = new Mat();
    Imgproc.medianBlur(img.mat, mat, size);
    return images.matToImage(mat);
}


images.gaussianBlur = function (img: Image, size: number[],
    sigmaX?: number, sigmaY?: number, type?: string) {
    initIfNeeded();
    var mat = new Mat();
    size = newSize(size);
    sigmaX = sigmaX == undefined ? 0 : sigmaX;
    sigmaY = sigmaY == undefined ? 0 : sigmaY;
    type = Core["BORDER_" + (type || "DEFAULT")];
    Imgproc.GaussianBlur(img.mat, mat, size, sigmaX, sigmaY, type);
    return images.matToImage(mat);
}

images.cvtColor = function (img: Image, code: string, dstCn?: number) {
    initIfNeeded();
    var mat = new Mat();
    code = Imgproc["COLOR_" + code];
    if (dstCn == undefined) {
        Imgproc.cvtColor(img.mat, mat, code);
    } else {
        Imgproc.cvtColor(img.mat, mat, code, dstCn);
    }
    return images.matToImage(mat);
}

images.findCircles = function (grayImg: Image, options: {
    region?: [number, number, number?, number?],
    dp?: number,
    minDst?: number,
    param1?: number,
    param2?: number,
    minRadius?: number,
    maxRadius?: number
}) {
    initIfNeeded();
    options = options || {};
    var mat = options.region == undefined ? grayImg.mat : new Mat(grayImg.mat, buildRegion(options.region, grayImg));
    var resultMat = new Mat()
    var dp = options.dp == undefined ? 1 : options.dp;
    var minDst = options.minDst == undefined ? grayImg.height / 8 : options.minDst;
    var param1 = options.param1 == undefined ? 100 : options.param1;
    var param2 = options.param2 == undefined ? 100 : options.param2;
    var minRadius = options.minRadius == undefined ? 0 : options.minRadius;
    var maxRadius = options.maxRadius == undefined ? 0 : options.maxRadius;
    Imgproc.HoughCircles(mat, resultMat, Imgproc.CV_HOUGH_GRADIENT, dp, minDst, param1, param2, minRadius, maxRadius);
    var result = [];
    for (var i = 0; i < resultMat.rows(); i++) {
        for (var j = 0; j < resultMat.cols(); j++) {
            var d = resultMat.get(i, j);
            result.push({
                x: d[0],
                y: d[1],
                radius: d[2]
            });
        }
    }
    if (options.region != undefined) {
        mat.release();
    }
    resultMat.release();
    return result;
}

images.resize = function (img: Image, size: [number, number?], interpolation?: string) {
    initIfNeeded();
    var mat = new Mat();
    interpolation = Imgproc["INTER_" + (interpolation || "LINEAR")];
    Imgproc.resize(img.mat, mat, newSize(size), 0, 0, interpolation);
    return images.matToImage(mat);
}

images.scale = function (img: Image, fx: number, fy: number, interpolation?: string) {
    initIfNeeded();
    var mat = new Mat();
    interpolation = Imgproc["INTER_" + (interpolation || "LINEAR")];
    Imgproc.resize(img.mat, mat, newSize([0, 0]), fx, fy, interpolation);
    return images.matToImage(mat);
}

images.rotate = function (img: Image, degree: number, x?: number, y?: number) {
    initIfNeeded();
    if (x == undefined) {
        x = img.width / 2;
    }
    if (y == undefined) {
        y = img.height / 2;
    }
    return javaImages.rotate(img, x, y, degree);
}

images.concat = function (img1: Image, img2: Image, direction?: string) {
    initIfNeeded();
    direction = direction || "right";
    return javaImages.concat(img1, img2, android.view.Gravity[direction.toUpperCase()]);
}

images.detectsColor = function (img: Image, color: Color,
    x: number, y: number, threshold?: number, algorithm?: string) {
    initIfNeeded();
    color = parseColor(color);
    algorithm = algorithm || "diff";
    threshold = threshold || defaultColorThreshold;
    var colorDetector = getColorDetector(color, algorithm, threshold);
    var pixel = (images as any).pixel(img, x, y);
    return colorDetector.detectsColor(colors.red(pixel), colors.green(pixel), colors.blue(pixel));
}

images.findColor = function (img: Image, color: Color, options?: ImageFindOptions & {
    similarity?: number
}) {
    initIfNeeded();
    color = parseColor(color);
    options = options || {};
    var region = options.region || [];
    if (options.similarity) {
        var threshold = 255 * (1 - options.similarity)
    } else {
        var threshold = options.threshold || defaultColorThreshold;
    }
    if (options.region) {
        return colorFinder.findColor(img, color, threshold, buildRegion(options.region, img));
    } else {
        return colorFinder.findColor(img, color, threshold, null);
    }
}

images.findColorInRegion = function (img: Image, color: Color,
    x: number, y: number, width?: number, height?: number, threshold?: number) {
    return images.findColor(img, color, {
        region: [x, y, width, height],
        threshold: threshold
    });
}

images.findColorEquals = function (img: Image, color: Color, x: number, y: number,
    width?: number, height?: number) {
    return images.findColor(img, color, {
        region: [x, y, width, height],
        threshold: 0
    });
}

images.findAllPointsForColor = function (img: Image, color: Color, options?: ImageFindOptions & {
    similarity?: number
}) {
    initIfNeeded();
    color = parseColor(color);
    options = options || {};
    if (options.similarity) {
        var threshold = (255 * (1 - options.similarity));
    } else {
        var threshold = options.threshold || defaultColorThreshold;
    }
    if (options.region) {
        return toPointArray(colorFinder.findAllPointsForColor(img, color, threshold, buildRegion(options.region, img)));
    } else {
        return toPointArray(colorFinder.findAllPointsForColor(img, color, threshold, null));
    }
}

images.findMultiColors = function (img: Image, firstColor: number | string,
    paths: [number, number, number | string][], options: ImageFindOptions) {
    initIfNeeded();
    options = options || {};
    firstColor = parseColor(firstColor);
    var list = java.lang.reflect.Array.newInstance(java.lang.Integer.TYPE, paths.length * 3);
    for (var i = 0; i < paths.length; i++) {
        var p = paths[i];
        list[i * 3] = p[0];
        list[i * 3 + 1] = p[1];
        list[i * 3 + 2] = parseColor(p[2]);
    }
    var region = options.region ? buildRegion(options.region, img) : null;
    var threshold = options.threshold === undefined ? defaultColorThreshold : options.threshold;
    return colorFinder.findMultiColors(img, firstColor, threshold, region, list);
}

images.findImage = function (img: Image, template: Image, options?: {
    threshold?: number,
    region?: [number, number, number?, number?],
    level?: number,
    weakThreshold?: number
}) {
    initIfNeeded();
    options = options || {};
    var threshold = options.threshold || 0.9;
    var maxLevel = -1;
    if (typeof (options.level) == 'number') {
        maxLevel = options.level;
    }
    var weakThreshold = options.weakThreshold || 0.6;
    if (options.region) {
        return javaImages.findImage(img, template, weakThreshold, threshold, buildRegion(options.region, img), maxLevel);
    } else {
        return javaImages.findImage(img, template, weakThreshold, threshold, null, maxLevel);
    }
}

images.matchTemplate = function (img: Image, template: Image, options?: {
    threshold?: number,
    region?: [number, number, number?, number?],
    max?: number,
    level?: number,
    weakThreshold?: number
}) {
    initIfNeeded();
    options = options || {};
    var threshold = options.threshold || 0.9;
    var maxLevel = -1;
    if (typeof (options.level) == 'number') {
        maxLevel = options.level;
    }
    var max = options.max || 5;
    var weakThreshold = options.weakThreshold || 0.6;
    var result;
    if (options.region) {
        result = javaImages.matchTemplate(img, template, weakThreshold, threshold, buildRegion(options.region, img), maxLevel, max);
    } else {
        result = javaImages.matchTemplate(img, template, weakThreshold, threshold, null, maxLevel, max);
    }
    return new MatchingResult(result);
}



images.findImageInRegion = function (img: Image, template: Image,
    x: number, y: number, width?: number, height?: number, threshold?: number) {
    return images.findImage(img, template, {
        region: [x, y, width, height],
        threshold: threshold
    });
}

images.fromBase64 = function (base64: string): Image {
    return javaImages.fromBase64(base64);
}

images.toBase64 = function (img: Image, format?: ImageFormat, quality?: number) {
    format = format || "png";
    quality = quality == undefined ? 100 : quality;
    return javaImages.toBase64(img, format, quality);
}

images.fromBytes = function (bytes: any): Image {
    return javaImages.fromBytes(bytes);
}

images.toBytes = function (img: Image, format?: ImageFormat, quality?: number) {
    format = format || "png";
    quality = quality == undefined ? 100 : quality;
    return javaImages.toBytes(img, format, quality);
}

images.readPixels = function (path: string) {
    var img = (images as any).read(path);
    var bitmap = img.getBitmap();
    var w = bitmap.getWidth();
    var h = bitmap.getHeight();
    var pixels = util.java.array("int", w * h);
    bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
    img.recycle();
    return {
        data: pixels,
        width: w,
        height: h
    };
}

images.matToImage = function (img: Image) {
    initIfNeeded();
    return ImageWrapper.ofMat(img);
}





function getColorDetector(color: Color, algorithm: unknown, threshold?: number) {
    switch (algorithm) {
        case "rgb":
            return new com.stardust.autojs.core.image.ColorDetector.RGBDistanceDetector(color, threshold);
        case "equal":
            return new com.stardust.autojs.core.image.ColorDetector.EqualityDetector(color);
        case "diff":
            return new com.stardust.autojs.core.image.ColorDetector.DifferenceDetector(color, threshold);
        case "rgb+":
            return new com.stardust.autojs.core.image.ColorDetector.WeightedRGBDistanceDetector(color, threshold);
        case "hs":
            return new com.stardust.autojs.core.image.ColorDetector.HSDistanceDetector(color, threshold);
    }
    throw new Error("Unknown algorithm: " + algorithm);
}


function toPointArray(points: unknown[]) {
    var arr = [];
    for (var i = 0; i < points.length; i++) {
        arr.push(points[i]);
    }
    return arr;
}

function buildRegion(region: (number | undefined)[], img: Image) {
    if (region == undefined) {
        region = [];
    }
    var x = region[0] === undefined ? 0 : region[0];
    var y = region[1] === undefined ? 0 : region[1];
    var width = region[2] === undefined ? img.width - x : region[2];
    var height = region[3] === undefined ? (img.height - y) : region[3];
    var r = new org.opencv.core.Rect(x, y, width, height);
    if (x < 0 || y < 0 || x + width > img.width || y + height > img.height) {
        throw new Error("out of region: region = [" + [x, y, width, height] + "], image.size = [" + [img.width, img.height] + "]");
    }
    return r;
}

function parseColor(color: Color): number {
    if (typeof (color) == 'string') {
        color = colors.parseColor(color) as number;
    }
    return color;
}

function newSize(size: Array<unknown>) {
    if (!Array.isArray(size)) {
        size = [size, size];
    }
    if (size.length == 1) {
        size = [size[0], size[0]];
    }
    return new Size(size[0], size[1]);
}

function initIfNeeded() {
    javaImages.initOpenCvIfNeeded();
}

asGlobal(images, ['requestScreenCapture', 'captureScreen', 'findImage', 'findImageInRegion',
    'findColor', 'findColorInRegion', 'findColorEquals', 'findMultiColors']);

declare global {
    var colors: any
    var requestScreenCapture: typeof images['requestScreenCapture']
    var captureScreen: () => Image
    var findImage: typeof images['findImage']
    var findImageInRegion: typeof images['findImageInRegion']
    var findColor: typeof images['findColor']
    var findColorInRegion: typeof images['findColorInRegion']
    var findColorEquals: typeof images['findColorEquals']

}
global.colors = colors;

export default images;

