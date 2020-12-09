package com.shepshook.shopper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.shepshook.shopper.data.ShopperDatabase;
import com.shepshook.shopper.data.entity.Product;

import java.util.List;
import java.util.Random;

public class NotificationWorker extends Worker {
    private final Context context;
    private final WorkerParameters parameters;
    private final String CHANNEL_ID = "SHOPPER";

    public NotificationWorker(Context context, WorkerParameters parameters) {
        super(context, parameters);
        this.context = context;
        this.parameters = parameters;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("Worker", "Worker starting");
        ShopperDatabase db = ShopperDatabase.getInstance(context);

        List<Product> list = db.productDao().getAllProducts();
        Random rand = new Random();
        Product p = list.get(rand.nextInt(list.size()));
        makeNotification(String.format("You'll definitely like our product: %s for $%s", p.name, p.price), context);

        return Result.success();
    }

    private void makeNotification(String message, Context context) {
        Log.d("Worker", "Composing notification");
        NotificationManager manager = (NotificationManager) context.getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Shopper Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_shopping_cart_24)
                .setContentTitle("Shopper")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(context).notify(1, notification);
        manager.notify(1, notification);
    }
}
