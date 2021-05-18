package org.internship.dating.bot.bot.common;

import java.util.Optional;
import javax.annotation.Nonnull;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class BotCommand<CommandNameT> {

    private final CommandNameT name;

    private final List<String> args;

    private final String raw;

    private BotCommand(
        @Nonnull CommandNameT name,
        @Nonnull List<String> args,
        @Nonnull String raw
    ) {
        this.name = requireNonNull(name, "name");
        this.args = requireNonNull(args, "args");
        this.raw = requireNonNull(raw, "raw");
    }

    @Nonnull
    public CommandNameT name() {
        return name;
    }

    @Nonnull
    public List<String> args() {
        return args;
    }

    @Nonnull
    public Optional<String> arg(int number) {
        return args.size() > number
            ? Optional.of(args.get(number))
            : Optional.empty();
    }

    @Nonnull
    public String raw() {
        return raw;
    }

    @Nonnull
    @Override
    public String toString() {
        return "Command{" +
            "name=" + name +
            ", args=" + args +
            ", raw=" + raw +
            '}';
    }

    public static class Builder<CommandNameT> {

        private CommandNameT name;
        private List<String> args;
        private String raw;

        private Builder() {
        }

        @Nonnull
        public static <CommandNameT> Builder<CommandNameT> command() {
            return new Builder<>();
        }

        @Nonnull
        public Builder<CommandNameT> name(@Nonnull CommandNameT name) {
            this.name = name;
            return this;
        }

        @Nonnull
        public Builder<CommandNameT> args(@Nonnull List<String> args) {
            this.args = args;
            return this;
        }

        @Nonnull
        public Builder<CommandNameT> raw(@Nonnull String raw) {
            this.raw = raw;
            return this;
        }

        @Nonnull
        public BotCommand<CommandNameT> build() {
            return new BotCommand<>(
                name,
                args,
                raw
            );
        }

    }

}
