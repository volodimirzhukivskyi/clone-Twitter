import * as React from 'react';
import DialogContentText from '@mui/material/DialogContentText';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import {MenuItem} from "@mui/material";
import {months, days} from "./birthData";
import years from "./birthData";
import PropTypes from "prop-types";
import {useSelector} from "react-redux";
import {getCustomizationTheme} from "../../../../redux/user/selector";
import {BACKGROUND} from "../../../../utils/theme";

const DateOfBirth = (props) => {
    const {backgroundColor} = useSelector(getCustomizationTheme);
    const themeTextColor = BACKGROUND[backgroundColor]?.palette.textColor;

    const {
        month, setMonth,
        day, setDay,
        year, setYear,
    } = props;

    return (
        <>
            <DialogContentText sx={{paddingTop: "10px", fontWeight: 600, color: themeTextColor}}>Date of birth </DialogContentText>
            <DialogContentText sx={{fontSize: 14, color: themeTextColor, marginBottom: "10px"}}>
                This will not be shown publicly. Confirm your own age, even if this account is for a business, a pet, or
                something else.
            </DialogContentText>
            <Grid container>
                <Grid item xs={5} sx={{padding: '10px 0'}}>
                    <TextField
                        select sx={{width: '100%'}}
                        id="month"
                        label="Month"
                        variant="outlined"
                        value={month}
                        onChange={setMonth}
                    >
                        {months.map((option) => (
                            <MenuItem sx={{position: "relative", zIndex: "101", color: themeTextColor}} key={option.value} value={option.value}>
                                {option.label}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={3} sx={{padding: '10px 20px'}}>
                    <TextField
                        select sx={{width: '100%'}}
                        id="day"
                        label="Day"
                        variant="outlined"
                        value={day}
                        onChange={setDay}
                    >
                        {days.map((option) => (
                            <MenuItem key={option.value} value={String(option.value)}>
                                {option.label}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
                <Grid item xs={4} sx={{padding: '10px 0'}}>
                    <TextField
                        select sx={{width: '100%'}}
                        id="year"
                        label="Year"
                        variant="outlined"
                        value={year}
                        onChange={setYear}
                    >
                        {years.map((option) => (
                            <MenuItem key={option.value} value={String(option.value)}>
                                {option.label}
                            </MenuItem>
                        ))}
                    </TextField>
                </Grid>
            </Grid>
        </>
    )
};

DateOfBirth.propTypes = {
    month: PropTypes.string,
    setMonth: PropTypes.func,
    day: PropTypes.string,
    setDay: PropTypes.func,
    year: PropTypes.string,
    setYear: PropTypes.func
}

export default DateOfBirth;
