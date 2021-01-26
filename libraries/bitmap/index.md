# Bitmap
Android SDK Bitmaps. You can find the documentation [here](https://developer.android.com/topic/performance/graphics).

I added methods
- Bitmap.from(Object) (static) that will do its best to convert the object into a Bitmap
- Bitmap.getAt(Int x, Int y) (`bitmap[x, y]`) to access a pixel easily
- Bitmap.putAt(List xy, Int y) (`bitmap[x, y] = 0xffffff`) to modify a pixel easily
