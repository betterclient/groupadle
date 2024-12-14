package io.github.betterclient.groupadle.apps.settings_unused;

import org.teavm.jso.JSBody;
import org.teavm.jso.core.JSPromise;

import java.util.function.Consumer;

public class BackgroundSelectionHandler {
    public static void start(Consumer<String> returnValueHandler) {
        //huzz the buzz

        giveMeValue().then(s -> {
            returnValueHandler.accept(s);
            return null;
        }).catchError(o -> {
            System.out.println("sadding: " + o);
            //sad;
            return o;
        });
    }

    @JSBody(script = """
            return (async function() {
                const { load0 } = await import("./filehelper.js");
                return load0();
            })();
            """)
    public static native JSPromise<String> giveMeValue();

    /*
    const fileInput = document.getElementById('fileInput');
    const resultDiv = document.getElementById('result');

    fileInput.addEventListener('change', async (event) => {
        const file = event.target.files[0];
        if (file) {
            try {
                // Convert to PNG and Base64
                const base64 = await convertImageToPNGBase64(file);
                resultDiv.innerHTML = `
                    <p>Base64:</p>
                    <textarea rows="10" cols="50">${base64}</textarea>
                    <p>Preview:</p>
                    <img src="${base64}" alt="Converted Image" />
                `;
            } catch (error) {
                console.error('Error:', error);
            }
        }
    });

    async function convertImageToPNGBase64(file) {
        // Load image into an <img> element
        const img = await loadImage(file);

        // Draw the image onto a <canvas>
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0);

        // Convert canvas to PNG and get Base64
        return canvas.toDataURL('image/png');
    }

    function loadImage(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => {
                const img = new Image();
                img.onload = () => resolve(img);
                img.onerror = reject;
                img.src = reader.result;
            };
            reader.onerror = reject;
            reader.readAsDataURL(file);
        });
    }
     */
}
