//prints coords at mouse click DEBUG ONLY
        quiltPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                Point p = event.getPoint();
                System.out.println(p);
            }
        });