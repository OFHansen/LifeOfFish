module presentation.lifeoffish {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens presentation.lifeoffish to javafx.fxml;
    exports presentation.lifeoffish;
    exports Domain;
    opens Domain to javafx.fxml;
}